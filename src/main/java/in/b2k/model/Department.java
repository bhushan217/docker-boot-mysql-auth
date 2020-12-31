package in.b2k.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Cacheable
@Entity
@Table(name = "B2K_DEPARTMENT")
public class Department extends Auditable {

    @Column(name = "NAME", length = 31, updatable = false, nullable = false)
    private String name;


}
