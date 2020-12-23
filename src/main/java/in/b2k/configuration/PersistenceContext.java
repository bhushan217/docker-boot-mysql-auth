package in.b2k.configuration;

import in.b2k.audit.AuditingDateTimeProvider;
import in.b2k.audit.UsernameAuditorAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaAuditing(dateTimeProviderRef = "dateTimeProvider", auditorAwareRef = "auditorProvider")
@EnableJpaRepositories(basePackages = {"in.b2k.repository"})
@EnableTransactionManagement
public class PersistenceContext {

    final AuditingDateTimeProvider auditingDateTimeProvider;

    public PersistenceContext(AuditingDateTimeProvider auditingDateTimeProvider) {
        this.auditingDateTimeProvider = auditingDateTimeProvider;
    }

    @Bean
    AuditorAware<String> auditorProvider() {
        return new UsernameAuditorAware();
    }
    @Bean
    DateTimeProvider dateTimeProvider() {
        return auditingDateTimeProvider;
    }

}
