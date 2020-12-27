package in.b2k;


import in.b2k.model.User;
import in.b2k.repository.UserRepository;
import in.b2k.security.config.UserAuthenticationService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@Service
@AllArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
@Slf4j
public class UUIDAuthenticationService implements UserAuthenticationService {

    @NonNull
    UserRepository userRepository;

    @Override
    public Optional<String> login(final String username, final String password) {

        final String uuid = UUID.randomUUID().toString();
        Optional<User> user = userRepository.findByUsernameAndPassword(username, password);
        if (user.isPresent()) {
            User userToUpdate = user.get();
            userToUpdate.setEnabled(true);
            userToUpdate.setToken(uuid);
            User updatedRecs = userRepository.save(userToUpdate);//updateTokenById(uuid, true, user.get().getId());
            log.debug("updatedRecs {}", updatedRecs);
            return Optional.of(uuid);
        } else {
            return Optional.of(null);
        }
    }

    @Override
    public Optional<User> findByToken(final String token) {
        log.debug("find by token {}", token);
        Optional<User> user = userRepository.findByToken(token);
        return user;
    }

    @Override
    public void logout(final User user) {
        log.debug("IN : logout user {}", user);
        userRepository.logout(user.getUsername());
    }
}
