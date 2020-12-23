package in.b2k.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "B2K_DEPARTMENT")
public class Department extends BaseEntity{

    @Column(name = "NAME", length = 31, updatable = false, nullable = false)
    private String name;


}
