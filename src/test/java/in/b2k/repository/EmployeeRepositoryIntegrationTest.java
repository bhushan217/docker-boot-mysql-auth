package in.b2k.repository;

import in.b2k.model.Employee;
import in.b2k.model.enums.Rating;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class EmployeeRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EmployeeRepository employeeRepository;


    @Test
    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
    public void whenFindByName_thenReturnEmployee() {
        // given
        Employee employee = new Employee("Amit", "Kamathe", "amit@gmail.com", Rating.FOUR);
        entityManager.persist(employee);
        entityManager.flush();

        // when
        Employee found = employeeRepository.findByFirstName(employee.getFirstName());

        // then
        assertThat(found.getFirstName(),is(employee.getFirstName()));
    }
}