package in.b2k.request.vo;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class UserVO extends BaseVO{

    private String name;
    private String username;
    private String password;
}
