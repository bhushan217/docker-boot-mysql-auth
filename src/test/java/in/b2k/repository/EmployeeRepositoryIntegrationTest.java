package in.b2k.repository;

import in.b2k.configuration.TestAuditingConfigurationB2k;
import in.b2k.configuration.WithMockAppUser;
import in.b2k.model.Department;
import in.b2k.model.Employee;
import in.b2k.model.enums.Rating;
import lombok.NonNull;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.AuditorAware;
import org.springframework.test.context.ActiveProfiles;

import static in.b2k.utils.B2kConstatnt.TEST_AUDITOR;
import static org.hamcrest.Matchers.is;

@DataJpaTest()
@ActiveProfiles("test")
@Import({TestAuditingConfigurationB2k.class})
public class EmployeeRepositoryIntegrationTest {


    @Autowired
    private AuditorAware<String> auditorAware;

    @NonNull
    @Autowired
    private DepartmentRepository departmentRepository;

    @NonNull
    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void testCurrentAuditor() {
        String currentAuditor = auditorAware.getCurrentAuditor().get();
        MatcherAssert.assertThat(TEST_AUDITOR, is(currentAuditor));
    }

    @Test
    @WithMockAppUser(username =  "bhushan")//, name = "Kmathe")
    public void whenFindByName_thenReturnEmployee() {
        // given
        Department dept = new Department("IT");
        departmentRepository.save(dept);
        Employee employee = new Employee("Amit", "Kamathe", "amit@gmail.com", Rating.FOUR, dept);
        employeeRepository.save(employee);

        // when
        Employee found = employeeRepository.findByFirstName(employee.getFirstName());

        // then
        MatcherAssert.assertThat(found.getFirstName(),is(employee.getFirstName()));
    }
}