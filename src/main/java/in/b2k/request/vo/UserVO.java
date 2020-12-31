package in.b2k.request.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class UserVO extends BaseVO{

    private String name;
    private String username;
    private String password;
}
