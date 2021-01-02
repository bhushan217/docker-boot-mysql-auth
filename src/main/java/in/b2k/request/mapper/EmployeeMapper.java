package in.b2k.request.mapper;


import in.b2k.model.Department;
import in.b2k.model.Employee;
import in.b2k.request.vo.DepartmentVO;
import in.b2k.request.vo.EmployeeVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface EmployeeMapper {

    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);
    @Mappings({ @Mapping(source = "employeeVO.id", target = "id") })
    Employee toEntity(EmployeeVO employeeVO);
    @Mappings({ @Mapping(source = "employee.id", target = "id") })
    EmployeeVO toVO(Employee employee);
    @Mappings({ @Mapping(source = "departmentVO.id", target = "id") })
    Department toDepartmentEntity(DepartmentVO departmentVO);
    @Mappings({ @Mapping(source = "department.id", target = "id") })
    DepartmentVO toDepartmentVO(Department department);

    @Mappings({
            @Mapping(source = "employee.id", target = "id"),
            @Mapping(source = "employeeVO.department", target = "department", ignore = true),
            @Mapping(source = "employeeVO.firstName", target = "firstName"),
            @Mapping(source = "employeeVO.lastName", target = "lastName"),
            @Mapping(source = "employeeVO.emailId", target = "emailId"),
            @Mapping(source = "employeeVO.rating", target = "rating")
    })
    Employee toEntity(Employee employee, EmployeeVO employeeVO);
    List<Employee> toEntityList(List<EmployeeVO> employeeVOList);
    List<EmployeeVO> toVOList(List<Employee> employeeList);
}
