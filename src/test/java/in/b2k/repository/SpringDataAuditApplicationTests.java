package in.b2k.repository;

import in.b2k.configuration.TestAuditingConfigurationB2k;
import in.b2k.model.User;
import in.b2k.repository.UserRepository;
import org.exparity.hamcrest.date.ZonedDateTimeMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import static in.b2k.utils.B2kConstatnt.TEST_AUDITOR;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.jgroups.util.Util.assertNotNull;

@DataJpaTest()
@ActiveProfiles("test")
@Import({TestAuditingConfigurationB2k.class})
public class SpringDataAuditApplicationTests {

    @Autowired
    private UserRepository userRepository;

    private User user;
    private String USERNAME;


    @BeforeEach
    public void create() {
        USERNAME = "bhushan217";
        user = userRepository.save(User.builder().name("Bhushan Kamathe").username(USERNAME).build());

        assertNotNull(user.getCreatedAt());

        assertNotNull(user.getUpdatedAt());

        String auditor = TEST_AUDITOR;
        assertThat(user.getCreatedBy(),is(equalTo(auditor)));

        assertThat(user.getUpdatedBy(),is(equalTo(auditor)));
    }

    @Test
    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
    public void update() {
        ZonedDateTime created = user.getCreatedAt();
        ZonedDateTime modified = user.getUpdatedAt();
        user.setUsername(USERNAME);
        userRepository.save(user);

        userRepository.findById(user.getId())
                .ifPresent(updatedUser -> {

                    assertThat(updatedUser.getUsername(), is(equalTo(USERNAME)));

                    assertThat(updatedUser.getCreatedAt(), ZonedDateTimeMatchers.within(1, ChronoUnit.SECONDS, created));//is(equalTo(created)));

                    assertThat(updatedUser.getUpdatedAt(), ZonedDateTimeMatchers.within(1, ChronoUnit.SECONDS, modified));
                });
    }
}
