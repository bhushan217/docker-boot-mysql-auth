package in.b2k.request.mapper;

import in.b2k.model.enums.Rating;
import in.b2k.request.vo.EmployeeVO;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class EmployeeMapperTest {

    @Test
    public void shouldMapVOToEntity(){
        //given
        final var employeeVo = new EmployeeVO(UUID.randomUUID(), "Empname", "Empname", "Empname", Rating.FOUR);
        //when
        final var employee = EmployeeMapper.INSTANCE.toEntity(employeeVo);
        //then
        assertThat(employee.getFirstName(), is(employeeVo.getFirstName()));
    }
}