package in.b2k.request.mapper;

import in.b2k.model.Department;
import in.b2k.model.Employee;
import in.b2k.model.enums.Rating;
import in.b2k.request.vo.DepartmentVO;
import in.b2k.request.vo.EmployeeVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@Slf4j
class EmployeeMapperTest {

    @Test
    public void shouldMapVOToEntity(){
        //given
        final var employeeId = UUID.randomUUID();
        final var departmentId = UUID.randomUUID();
        DepartmentVO departmentVO = DepartmentVO.builder().id(departmentId).name("IT").build();
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
        assertThat("employee not null", employee!=null);
        assertThat(employee.getId(), is(employeeVO.getId()));
        assertThat(employee.getFirstName(), is(employeeVO.getFirstName()));
        assertThat(employee.getLastName(), is(employeeVO.getLastName()));
        assertThat(employee.getEmailId(), is(employeeVO.getEmailId()));
        assertThat(employee.getRating(), is(employeeVO.getRating()));
        assertThat("department not null", employeeVO.getDepartment()!=null);
        assertThat(employee.getDepartment().getName(), is(employeeVO.getDepartment().getName()));
        assertThat("departmentId not null", employeeVO.getDepartment().getId()!=null);
        assertThat(employee.getDepartment().getId(), is(employeeVO.getDepartment().getId()));
    }

    @Test
    public void shouldMapEntityToVO(){
        //given
        final var employeeId = UUID.randomUUID();
        final var departmentId = UUID.randomUUID();
        Department department = Department.builder().id(departmentId).name("IT").build();
        final var employee = Employee.builder()
                .id(employeeId)
                .firstName("Vikas")
                .lastName("Kamthe")
                .emailId("emp@department.in")
                .rating(Rating.FOUR)
                .department(department)
                .build();
        //when
        final var employeeVO = EmployeeMapper.INSTANCE.toVO(employee);
        log.info("employeeVO {}\n employee{}", employeeVO, employee);
        //then
        assertThat("employeeVO not null", employeeVO!=null);
        assertThat(employeeVO.getId(), is(employee.getId()));
        assertThat(employeeVO.getFirstName(), is(employee.getFirstName()));
        assertThat(employeeVO.getLastName(), is(employee.getLastName()));
        assertThat(employeeVO.getEmailId(), is(employee.getEmailId()));
        assertThat(employeeVO.getRating(), is(employee.getRating()));
        assertThat("departmentVO not null", employee.getDepartment()!=null);
        assertThat(employeeVO.getDepartment().getName(), is(employee.getDepartment().getName()));
        assertThat("departmentVO Id not null", employee.getDepartment().getId()!=null);
        assertThat(employeeVO.getDepartment().getId(), is(employee.getDepartment().getId()));
    }
}