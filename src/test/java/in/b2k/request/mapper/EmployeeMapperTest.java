package in.b2k.request.mapper;

import in.b2k.model.Department;
import in.b2k.model.Employee;
import in.b2k.model.enums.Rating;
import in.b2k.request.vo.DepartmentVO;
import in.b2k.request.vo.EmployeeVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class EmployeeMapperTest {

    @Test
    public void shouldMapVOToEntity(){
        //given
        final var employeeId = UUID.randomUUID();
        final var departmentId = UUID.randomUUID();
        final var departmentVO = DepartmentVO.builder().id(departmentId).name("IT").build();
        final var employeeVO = EmployeeVO.builder()
                .id(employeeId)
                .firstName("Vikas")
                .lastName("Kamthe")
                .emailId("emp@department.in")
                .rating(Rating.FOUR)
                .department(departmentVO)
                .build();
        //when
        final var employee = EmployeeMapper.INSTANCE.toEntity(employeeVO);
        log.info("employee {}\n employeeVO{}", employee, employeeVO);

        //then
        assertNotNull(employee, "employee is null");
        assertEquals(employee.getId(), employeeVO.getId());
        assertEquals(employee.getFirstName(), employeeVO.getFirstName());
        assertEquals(employee.getLastName(), employeeVO.getLastName());
        assertEquals(employee.getEmailId(), employeeVO.getEmailId());
        assertEquals(employee.getRating(), employeeVO.getRating());

        assertNotNull(employeeVO.getDepartment(), "department is null");
        assertEquals(employee.getDepartment().getName(), employeeVO.getDepartment().getName());
        assertEquals(employee.getDepartment().getId(), employeeVO.getDepartment().getId());
    }

    @Test
    public void shouldMapEntityToVO(){
        //given
        final var employeeId = UUID.randomUUID();
        final var departmentId = UUID.randomUUID();
        final var department = Department.builder().id(departmentId).name("IT").build();
        final var employee = Employee.builder()
                .id(employeeId)
                .firstName("Vikas")
                .lastName("Kamthe")
                .emailId("emp@department.b2k.in")
                .rating(Rating.FOUR)
                .department(department)
                .build();

        //when
        final var employeeVO = EmployeeMapper.INSTANCE.toVO(employee);
        log.info("employeeVO {}\n employee{}", employeeVO, employee);

        //then
        assertNotNull(employeeVO, "employeeVO is null");
        assertEquals(employeeVO.getId(), employee.getId());
        assertEquals(employeeVO.getFirstName(), employee.getFirstName());
        assertEquals(employeeVO.getLastName(), employee.getLastName());
        assertEquals(employeeVO.getEmailId(), employee.getEmailId());
        assertEquals(employeeVO.getRating(), employee.getRating());

        final var departmentR = employee.getDepartment();
        final var departmentVO = employeeVO.getDepartment();
        assertNotNull(departmentR, "departmentVO is null");
        assertEquals(departmentVO.getId(), departmentR.getId());
        assertEquals(departmentVO.getName(), departmentR.getName());
    }
}