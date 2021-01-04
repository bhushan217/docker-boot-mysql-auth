package in.b2k.controller;

import in.b2k.configuration.WithMockAppUser;
import in.b2k.model.enums.Rating;
import in.b2k.repository.DepartmentRepository;
import in.b2k.repository.EmployeeRepository;
import in.b2k.request.mapper.DepartmentMapper;
import in.b2k.request.vo.DepartmentVO;
import in.b2k.request.vo.EmployeeVO;
import lombok.SneakyThrows;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.Alphanumeric.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@WithMockAppUser(username = "TEST_USER")
@AutoConfigureMockMvc
@SpringBootTest//(classes = {SpringSecurityWebTestConfig.class, TestAuditingConfigurationB2k.class})//webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
class EmployeeControllerTest {

    @Autowired
    private EmployeeController employeeController;
    @Autowired
    private DepartmentController departmentController;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentRepository departmentRepository;

    private DepartmentVO departmentVO_Base;

    @Test
    //@Order(1)
    //@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
    public void A_contextLoads() throws Exception {
        assertThat(employeeController).isNotNull();
    }

    @SneakyThrows
    @Test
    //@Order(2)
    //@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
    void B_createDepartment() {

        final DepartmentVO departmentVO_IN = getDepartmentVO_in();
        ResponseEntity<DepartmentVO> responseEntity = departmentController.createDepartment(departmentVO_IN);

        assertNotNull(responseEntity);
        assertTrue(responseEntity.getStatusCode().equals(HttpStatus.OK));

        DepartmentVO departmentVO = responseEntity.getBody();
        Thread.sleep(100);//give activemq some time
    }

    @Test
    //@Order(3)
    //@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
    void C_getAllDepartments() {
        final var departments = departmentRepository.findAll();
        assertEquals(1, departments.size());
        departmentVO_Base = DepartmentMapper.INSTANCE.toVO(departments.get(0));
    }

    @SneakyThrows
    @Test
    //@Order(4)
    //@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
    void D_createEmployee() {

        final EmployeeVO employeeVO_IN = getEmployeeVO_in();
        employeeVO_IN.setDepartment(departmentVO_Base);
        ResponseEntity<EmployeeVO> responseEntity = employeeController.createEmployee(employeeVO_IN);

        assertNotNull(responseEntity);
        assertTrue(responseEntity.getStatusCode().equals(HttpStatus.OK));

        EmployeeVO employeeVO = responseEntity.getBody();
        Thread.sleep(100);//give activemq some time
    }

    @Test
    //@Order(5)
    //@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
    void E_getAllEmployees() {
        final var employees = employeeRepository.findAll();
        assertEquals(1, employees.size());
    }

    //@Test
    void getEmployeeById() {
    }

    //@Test
    void updateEmployee() {
    }

    //@Test
    void deleteEmployee() {
    }

    private DepartmentVO getDepartmentVO_in() {
        return DepartmentVO.builder().name("IT").build();
    }

    private EmployeeVO getEmployeeVO_in() {
        final var departmentVO_IN = DepartmentVO.builder().name("IT").build();
        final var employeeVO_IN = EmployeeVO.builder()
                .firstName("Vikas")
                .lastName("Kamthe")
                .emailId("emp@department.in")
                .rating(Rating.FOUR)
                .department(departmentVO_IN)
                .build();
        return employeeVO_IN;
    }
}