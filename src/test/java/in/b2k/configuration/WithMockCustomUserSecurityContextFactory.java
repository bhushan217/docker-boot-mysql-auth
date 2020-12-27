package in.b2k.configuration;

import in.b2k.audit.AppUserPrincipal;
import in.b2k.model.User;
import in.b2k.model.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import org.springframework.util.Assert;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockAppUser> {

    @Autowired
    public WithMockCustomUserSecurityContextFactory(){}

    public SecurityContext createSecurityContext(WithMockAppUser customUser) {
        String username = customUser.username();
        Assert.hasLength(username, "value() must be non empty String");
        User user =  User.builder().username(username).password(username).name(username)
                .enabled(true).token("my_secret_token").role(Role.USER).build();
        AppUserPrincipal principal = new AppUserPrincipal(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(principal, principal.getPassword(), principal.getAuthorities());
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        return context;
    }
}