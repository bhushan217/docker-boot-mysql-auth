package in.b2k.controller;

import in.b2k.configuration.utils.AuthenticationUtil;
import in.b2k.exception.ResourceNotFoundException;
import in.b2k.model.Employee;
import in.b2k.repository.EmployeeRepository;
import in.b2k.request.mapper.EmployeeMapper;
import in.b2k.request.vo.EmployeeVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@PreAuthorize(value = "hasAuthority({'USER', 'ADMIN'})")
@RequestMapping("/api/employees")
@Slf4j
public class EmployeeController {

    private final EmployeeRepository employeeRepository;
    private final JmsTemplate employeeJmsTemplate;

    @Value("${jms.queue.destination.reg_employee}")
    String destQueueRegEmployee;

    public EmployeeController(JmsTemplate employeeJmsTemplate, EmployeeRepository employeeRepository) {
        this.employeeJmsTemplate = employeeJmsTemplate;
        this.employeeRepository = employeeRepository;
    }

    @GetMapping
    public List<EmployeeVO> getAllEmployees() {
        return toVOList(employeeRepository.findAll());
    }

    @GetMapping("/body")
    public ResponseEntity<EmployeeVO> getEmployeeById(@RequestBody EmployeeVO employeeVO)
            throws ResourceNotFoundException {
        UUID employeeId = employeeVO.getId();
        log.debug("getEmployeeById {}", employeeVO.getId());
        Employee employee = getEmployeeById(employeeId);
        return ResponseEntity.ok().body(EmployeeMapper.INSTANCE.toVO(employee));
    }

    @PostMapping()
    public ResponseEntity<EmployeeVO> createEmployee(@Valid @RequestBody EmployeeVO employeeVO) {
        Employee employee = toEntity(employeeVO);
        employeeJmsTemplate.convertAndSend(destQueueRegEmployee, employee , AuthenticationUtil.forwardAuthContext());
        return ResponseEntity.ok(toVO(employee));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeVO> updateEmployee(@PathVariable(value = "id") UUID employeeId,
                                                   @Valid @RequestBody EmployeeVO employeeVO) throws ResourceNotFoundException {
        var employee = getEmployeeById(employeeId);
        employee.setEmailId(employeeVO.getEmailId());
        employee.setLastName(employeeVO.getLastName());
        employee.setFirstName(employeeVO.getFirstName());
        final var updatedEmployee = employeeRepository.save(employee);
        return ResponseEntity.ok(toVO(updatedEmployee));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EmployeeVO> deleteEmployee(@PathVariable(value = "id") UUID employeeId)
            throws ResourceNotFoundException {
        Employee employee = getEmployeeById(employeeId);
        //var employeeVO = toVO(employee);
        employeeRepository.delete(employee);
        return ResponseEntity.ok(toVO(employee));
    }

    protected List<EmployeeVO> toVOList(List<Employee> employeeList) {
        return EmployeeMapper.INSTANCE.toVOList(employeeList);
    }

    protected Employee toEntity(EmployeeVO employeeVO) {
        return EmployeeMapper.INSTANCE.toEntity(employeeVO);
    }

    protected EmployeeVO toVO(Employee employee) {
        return EmployeeMapper.INSTANCE.toVO(employee);
    }

    protected Employee getEmployeeById(UUID employeeId) throws ResourceNotFoundException {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));
    }
}
