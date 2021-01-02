package in.b2k.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(of = "id")
@MappedSuperclass
public abstract class Auditable implements Serializable {

    @Id
    /*@GeneratedValue(generator = "UUID")  //postgresql
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator",
            parameters = { @Parameter(name = "uuid_gen_strategy_class",
                    value = "org.hibernate.id.uuid.CustomVersionOneStrategy")})
    @Column(name = "ID", updatable = false, nullable = false)*/
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)", name = "ID", updatable = false, nullable = false)
    private UUID id;

    @Version
    private Integer version;

    @Column(name = "CREATED_BY", length = 31, updatable = false, nullable = false)
    @CreatedBy
    private String createdBy;

    @Column(name = "CREATED_AT", updatable = false, nullable = false)
    @CreatedDate
    private ZonedDateTime createdAt;

    @Column(name = "UPDATED_BY", length = 31, nullable = false)
    @LastModifiedBy
    private String updatedBy;

    @Column(name = "UPDATED_AT", nullable = false)
    @LastModifiedDate
    private ZonedDateTime updatedAt;

}
