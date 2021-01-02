package in.b2k.controller;

import in.b2k.configuration.utils.AuthenticationUtil;
import in.b2k.exception.ResourceNotFoundException;
import in.b2k.model.Department;
import in.b2k.repository.DepartmentRepository;
import in.b2k.request.mapper.DepartmentMapper;
import in.b2k.request.vo.DepartmentVO;
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
@RequestMapping("/api/departments")
@Slf4j
public class DepartmentController {

    private final DepartmentRepository departmentRepository;
    private final JmsTemplate departmentJmsTemplate;

    @Value("${jms.queue.destination.reg_department}")
    String destQueueRegDepartment;

    public DepartmentController(JmsTemplate departmentJmsTemplate, DepartmentRepository departmentRepository) {
        this.departmentJmsTemplate = departmentJmsTemplate;
        this.departmentRepository = departmentRepository;
    }

    @GetMapping
    public List<DepartmentVO> getAllDepartments() {
        return toVOList(departmentRepository.findAll());
    }

    @GetMapping("/body")
    public ResponseEntity<DepartmentVO> getDepartmentById(@RequestBody DepartmentVO departmentVO)
            throws ResourceNotFoundException {
        UUID departmentId = departmentVO.getId();
        log.debug("getDepartmentById {}", departmentVO.getId());
        Department department = getDepartmentById(departmentId);
        return ResponseEntity.ok().body(DepartmentMapper.INSTANCE.toVO(department));
    }

    @PostMapping()
    public ResponseEntity<DepartmentVO> createDepartment(@Valid @RequestBody DepartmentVO departmentVO) {
        Department department = toEntity(departmentVO);
        departmentJmsTemplate.convertAndSend(destQueueRegDepartment, department , AuthenticationUtil.forwardAuthContext());
        return ResponseEntity.ok(toVO(department));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartmentVO> updateDepartment(@PathVariable(value = "id") UUID departmentId,
                                                     @Valid @RequestBody DepartmentVO departmentVO) throws ResourceNotFoundException {
        var department = getDepartmentById(departmentId);
        department.setName(departmentVO.getName());
        final var updatedDepartment = departmentRepository.save(department);
        return ResponseEntity.ok(toVO(updatedDepartment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DepartmentVO> deleteDepartment(@PathVariable(value = "id") UUID departmentId)
            throws ResourceNotFoundException {
        Department department = getDepartmentById(departmentId);
        var departmentVO = toVO(department);
        departmentRepository.delete(department);
        return ResponseEntity.ok(departmentVO);
    }

    protected List<DepartmentVO> toVOList(List<Department> departmentList) {
        return DepartmentMapper.INSTANCE.toVOList(departmentList);
    }

    protected Department toEntity(DepartmentVO departmentVO) {
        return DepartmentMapper.INSTANCE.toEntity(departmentVO);
    }

    protected DepartmentVO toVO(Department department) {
        return DepartmentMapper.INSTANCE.toVO(department);
    }

    protected Department getDepartmentById(UUID departmentId) throws ResourceNotFoundException {
        return departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found for this id :: " + departmentId));
    }

}
