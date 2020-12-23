package in.b2k.controller;

import in.b2k.model.User;
import in.b2k.model.enums.Role;
import in.b2k.repository.UserRepository;
import in.b2k.security.config.UserAuthenticationService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping("/public/users")
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
final class PublicUsersController {
    @NonNull
    UserAuthenticationService authentication;
    @NonNull
    UserRepository userRepository;

    @PostMapping("/register")
    String register(
            @RequestParam("name") final String name,
            @RequestParam("username") final String username,
            @RequestParam("password") final String password) {

        final String uuid = UUID.randomUUID().toString();

        userRepository.save(
                User.builder()
                        .token(uuid)
                        .name(name)
                        .enabled(false)
                        .role(Role.USER)
                        .username(username)
                        .password(password)
                        .build()
                );

        return login(username, password);
    }

    @PostMapping("/login")
    String login(
            @RequestParam("username") final String username,
            @RequestParam("password") final String password) {
        return authentication
                .login(username, password)
                .orElseThrow(() -> new RuntimeException("invalid login and/or password"));
    }
}