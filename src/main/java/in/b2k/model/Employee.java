package in.b2k.model;

import in.b2k.model.enums.Rating;
import lombok.*;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "B2K_EMPLOYEE")
public class Employee extends BaseEntity{

	@Column(name = "FIRST_NAME", length = 63, nullable = false)
	private String firstName;

	@Column(name = "LAST_NAME", length = 63, nullable = false)
	private String lastName;

	@Column(name = "EMAIL_ID", length = 63, nullable = false)
	private String emailId;

	@Enumerated(EnumType.STRING)
	private Rating rating;

	
}
