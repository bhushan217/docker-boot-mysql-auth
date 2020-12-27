package in.b2k.controller;

import in.b2k.model.User;
import in.b2k.security.config.UserAuthenticationService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping("/users")
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
@Slf4j
final class SecuredUsersController {
    @NonNull
    UserAuthenticationService authenticationService;

    @GetMapping("/current")
    UserDetails getCurrent(@AuthenticationPrincipal final UserDetails user) {
        log.info("getting current user info for {}", user.getUsername());
        return user;
    }

    @GetMapping("/logout")
    boolean logout(@AuthenticationPrincipal final UserDetails userDetails) {
        log.info("logging out current user for {}", userDetails.getUsername());
        User user = User.builder().username(userDetails.getUsername()).build();
        authenticationService.logout(user);
        log.info("logging out user :{}: SUCCESS", userDetails.getUsername());
        return true;
    }
}
