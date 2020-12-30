package in.b2k.request.mapper;


import in.b2k.model.Employee;
import in.b2k.request.vo.EmployeeVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmployeeMapper {

    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    Employee toEntity(EmployeeVO source);
    EmployeeVO toVO(Employee destination);
}
