package in.b2k.model;

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
@Table(name = "B2K_EMPLOYEE")
public class Employee extends Auditable {

    @Column(name = "FIRST_NAME", length = 63, nullable = false)
    private String firstName;

    @Column(name = "LAST_NAME", length = 63, nullable = false)
    private String lastName;

    @Column(name = "EMAIL_ID", length = 63, nullable = false)
    private String emailId;

    @Enumerated(EnumType.STRING)
    private Rating rating;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "DEPT_ID")
    private Department department;


}
