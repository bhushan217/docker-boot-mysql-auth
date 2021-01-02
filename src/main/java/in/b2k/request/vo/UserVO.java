package in.b2k.request.vo;

import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class UserVO extends BaseVO{

    private String name;
    private String username;
    private String password;
}
