package in.b2k.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UsernameAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {

        //return Optional.of("Mr. Auditor"); //test
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            //context.
            return Optional.of("ANONYMOUS");
        }

        AppUserPrincipal principal = (AppUserPrincipal) authentication.getPrincipal();
        return Optional.of(principal.getUsername());
    }

}
