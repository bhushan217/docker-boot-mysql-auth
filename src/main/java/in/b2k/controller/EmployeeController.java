package in.b2k.controller;

import in.b2k.exception.ResourceNotFoundException;
import in.b2k.model.Employee;
import in.b2k.repository.EmployeeRepository;
import in.b2k.request.vo.EmployeeVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
//@PreAuthorize(value = "hasAuthority('USER','ADMIN')")
@RequestMapping("/api")
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

    @GetMapping("/employees")
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @GetMapping("/employee")
    public ResponseEntity<Employee> getEmployeeById(@RequestBody EmployeeVO employeeVO)
            throws ResourceNotFoundException {
        UUID employeeId = employeeVO.getId();
        log.debug("getEmployeeById {}", employeeVO.getId());
        Employee employee = employeeRepository.findById(employeeVO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));
        return ResponseEntity.ok().body(employee);
    }

    @PostMapping("/employees")
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody Employee employee) {
        log.debug("Creating Employee: {}", employee);
        employeeJmsTemplate.convertAndSend(destQueueRegEmployee, employee);
        return ResponseEntity.ok().body(employee);
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") UUID employeeId,
                                                   @Valid @RequestBody Employee employeeDetails) throws ResourceNotFoundException {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));

        employee.setEmailId(employeeDetails.getEmailId());
        employee.setLastName(employeeDetails.getLastName());
        employee.setFirstName(employeeDetails.getFirstName());
        final Employee updatedEmployee = employeeRepository.save(employee);
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/employees/{id}")
    public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") UUID employeeId)
            throws ResourceNotFoundException {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));

        employeeRepository.delete(employee);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

}
