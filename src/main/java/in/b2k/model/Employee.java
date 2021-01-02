package in.b2k.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import in.b2k.model.enums.Rating;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Cacheable
@Entity
@Table(name = "B2K_EMPLOYEE", uniqueConstraints = @UniqueConstraint(name = "UK_B2K_EMP_EMAIL", columnNames = "EMAIL_ID"))
public class Employee extends Auditable {

    @Column(name = "FIRST_NAME", length = 63, nullable = false)
    private String firstName;

    @Column(name = "LAST_NAME", length = 63, nullable = false)
    private String lastName;

    @Column(name = "EMAIL_ID", length = 63, nullable = false)
    private String emailId;

    @Enumerated(EnumType.STRING)
    private Rating rating;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEPT_ID", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "FK_B2K_EMP_DEPT"))
    private Department department;

}
