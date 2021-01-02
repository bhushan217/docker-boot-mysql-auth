package in.b2k.model;


import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Cacheable
@Entity
@Table(name = "B2K_CATEGORY", uniqueConstraints = @UniqueConstraint(name = "UK_B2K_CATEGORY", columnNames = "NAME"))
public class Category extends Auditable{

    @Column(name = "NAME", length = 64)
    private String name;

    @Column(name = "DEPT_LEVEL")
    private short level;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID", foreignKey = @ForeignKey(name = "FK_B2K_CATEGORY"))
    private Category parent;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent", cascade = CascadeType.ALL)
    private Set<Category> children = new HashSet<>();

    public Category(String name, Category parent) {
        this.name = name;
        this.parent = parent;
    }
}
