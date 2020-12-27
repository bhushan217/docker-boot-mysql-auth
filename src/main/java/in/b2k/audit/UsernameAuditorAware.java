package in.b2k.audit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UsernameAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        log.debug("Getting the username of authenticated user.");
        //return Optional.of("Mr. Auditor"); //test
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            log.info("authentication not found {}", authentication);
            log.debug("Current user is anonymous. Returning null.");
            return Optional.of("ANONYMOUS");
        }

        AppUserPrincipal principal = (AppUserPrincipal) authentication.getPrincipal();
        return Optional.of(principal.getUsername());
    }

}
