package in.b2k.request.vo;

import in.b2k.model.enums.Rating;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class EmployeeVO extends BaseVO{

    private String firstName;
    private String lastName;
    private String emailId;
    private Rating rating;

    private DepartmentVO department;
}
