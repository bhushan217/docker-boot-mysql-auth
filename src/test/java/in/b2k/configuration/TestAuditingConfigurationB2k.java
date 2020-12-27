package in.b2k.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.ZonedDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.Optional;

import static in.b2k.utils.B2kConstatnt.TEST_AUDITOR;

@Configuration
@Profile("test")
@EnableJpaAuditing(auditorAwareRef = "testAuditorProvider", dateTimeProviderRef = "dateTimeProvider")
public class TestAuditingConfigurationB2k {

    @Bean
    @Primary
    public AuditorAware<String> testAuditorProvider() {
        return () -> Optional.of(TEST_AUDITOR);
    }

    @Bean
    @Primary
    public DateTimeProvider dateTimeProvider() {
        return () ->  Optional.<TemporalAccessor>of(ZonedDateTime.now());
    }

}
