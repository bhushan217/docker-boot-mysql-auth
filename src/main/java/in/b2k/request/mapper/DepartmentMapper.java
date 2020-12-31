package in.b2k.request.mapper;


import in.b2k.model.Department;
import in.b2k.request.vo.DepartmentVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DepartmentMapper {

    DepartmentMapper INSTANCE = Mappers.getMapper(DepartmentMapper.class);

    Department toEntity(DepartmentVO source);
    DepartmentVO toVO(Department destination);
}
