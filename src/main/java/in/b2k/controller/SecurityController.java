package in.b2k.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityController {

    @GetMapping(value = "/username")
    public String currentUserName(Authentication authentication) {
        return authentication.getName();
    }
}
