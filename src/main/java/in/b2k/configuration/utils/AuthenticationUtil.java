package in.b2k.configuration.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.SerializationUtils;

import javax.xml.bind.DatatypeConverter;
import java.util.Optional;

@Slf4j
public class AuthenticationUtil {
    public static final String AUTH_CONTEXT = "authContext";

    public static String serialize(Authentication authentication) {
        byte[] bytes = SerializationUtils.serialize(authentication);
        return DatatypeConverter.printBase64Binary(bytes);
    }

    public static Authentication deserialize(String authentication) {
        byte[] decoded = DatatypeConverter.parseBase64Binary(authentication);
        return (Authentication) SerializationUtils.deserialize(decoded);
    }

    public static MessagePostProcessor forwardAuthContext(){
        return (m) -> {
            var auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null) {
                String serialized = AuthenticationUtil.serialize(auth);
                m.setStringProperty(AuthenticationUtil.AUTH_CONTEXT, serialized);
                log.debug("setting custom JMS headers before sending : {}", serialized);
            } else {
                log.debug("Can't forward Authentication of type null");
            }
            return m;
        };
    }

    public static void setForwardedAuth(String authStr) {
        if(authStr!=null)
            SecurityContextHolder.getContext().setAuthentication(AuthenticationUtil.deserialize(authStr));
    }
}
