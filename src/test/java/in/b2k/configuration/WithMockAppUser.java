package in.b2k.configuration;

import org.springframework.security.test.context.support.WithSecurityContext;

@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface WithMockAppUser {

    String username() default "Bhushan";

    //String name() default "Bhushan Kamathe";
}
