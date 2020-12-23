package in.b2k.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import in.b2k.model.enums.Role;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.Constraint;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Entity
@ToString
@Table(name = "B2K_USER", uniqueConstraints = @UniqueConstraint(name = "UK_B2K_USERNAME", columnNames = "USERNAME"))
public class User extends BaseEntity{

    @Column(name = "USERNAME", unique = true, length = 31, updatable = false, nullable = false)
    @NotBlank(message = "username is required")
    protected String username;

    @Column(name = "NAME", length = 31, updatable = false, nullable = false)
    @NotBlank(message = "name is required")
    private String name;

    //@ColumnDefault(value="USER")
    @Column(name = "ROLE", nullable = false)
    @Enumerated(EnumType.STRING)
    protected Role role ;

    @Column(name = "PASSWORD")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    //@ColumnDefault(value="false")
    @Column(name = "IS_ACTIVE", nullable = false)
    private Boolean enabled ;

    @Column(name = "LAST_LOGIN")
    private LocalDateTime lastLogin;

    @Column(name = "TOKEN", length = 60)
    private String token;

}
