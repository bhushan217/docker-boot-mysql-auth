package in.b2k.controller;

import in.b2k.model.User;
import in.b2k.model.enums.Role;
import in.b2k.repository.UserRepository;
import in.b2k.request.vo.UserVO;
import in.b2k.security.config.UserAuthenticationService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping("/public/users")
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
@Slf4j
final class PublicUsersController {
    @NonNull
    UserAuthenticationService authentication;
    @NonNull
    UserRepository userRepository;

    //@PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/register")
    String register(@Valid @RequestBody UserVO userVO) {

        final String uuid = UUID.randomUUID().toString();
        log.info("/register {} {}", userVO.getUsername(), uuid);
        userRepository.save(
                User.builder()
                        .token(uuid)
                        .name(userVO.getName())
                        .enabled(true)
                        .role(Role.USER)
                        .username(userVO.getUsername())
                        .password(userVO.getPassword())
                        .build()
        );

        return uuid;//login(username, password);
    }

    @PostMapping("/login")
    String login(@Valid @RequestBody UserVO userVO) {
        log.info("attempting login for user {}", userVO.getUsername());
        return authentication
                .login(userVO.getUsername(), userVO.getPassword())
                .orElseThrow(() -> new RuntimeException("invalid login and/or password"));
    }
}