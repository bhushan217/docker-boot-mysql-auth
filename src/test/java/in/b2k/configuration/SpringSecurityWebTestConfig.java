package in.b2k.configuration;

import in.b2k.audit.AppUserDetailsService;
import in.b2k.audit.AppUserPrincipal;
import in.b2k.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Arrays;
import java.util.List;

@TestConfiguration
public class SpringSecurityWebTestConfig {

    @Autowired
    UserRepository userRepository;

    @Bean
    @Primary
    public UserDetailsService appUserDetailsService() {
        User basicUser = new User("Basic User",  "password", Arrays.asList(
                new SimpleGrantedAuthority("USER"),
                new SimpleGrantedAuthority("ADMIN")
        ));

        User managerUser = new User("Manager User", "password", Arrays.asList(
                new SimpleGrantedAuthority("USER"),
                new SimpleGrantedAuthority("ADMIN")
        ));

        List<UserDetails> users = Arrays.asList(basicUser, managerUser);
        return new InMemoryUserDetailsManager(users);
    }
}
