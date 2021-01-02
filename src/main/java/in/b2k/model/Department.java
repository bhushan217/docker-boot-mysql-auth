package in.b2k.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Set;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Cacheable
@Entity
@Table(name = "B2K_DEPARTMENT", uniqueConstraints = @UniqueConstraint(name = "UK_B2K_DEPARTMENT", columnNames = "NAME"))
public class Department extends Auditable {

    @Column(name = "NAME", length = 31, nullable = false)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Employee> employees;
}
