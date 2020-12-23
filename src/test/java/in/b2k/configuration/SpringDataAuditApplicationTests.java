package in.b2k.configuration;

import in.b2k.model.User;
import in.b2k.repository.UserRepository;
import org.exparity.hamcrest.date.ZonedDateTimeMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.jgroups.util.Util.assertNotNull;

@SpringBootTest
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

        String auditor = "Mr. Auditor";
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
