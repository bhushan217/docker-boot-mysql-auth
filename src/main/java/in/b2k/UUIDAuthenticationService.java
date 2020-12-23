package in.b2k;


import in.b2k.model.User;
import in.b2k.repository.UserRepository;
import in.b2k.security.config.UserAuthenticationService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@Service
@AllArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class UUIDAuthenticationService implements UserAuthenticationService {

    @NonNull
    UserRepository userRepository;

    @Override
    public Optional<String> login(final String username, final String password) {

        final String uuid = UUID.randomUUID().toString();
        Optional<User> user = userRepository.findByUsernameAndPassword(username, password);
        if(user.isPresent()){
            userRepository.updateTokenById(uuid, true, user.get().getId());
            return Optional.of(uuid);
        }else{
            return Optional.of(null);
        }
    }

    @Override
    public Optional<User> findByToken(final String token) {
        return userRepository.findByToken(token);
    }

    @Override
    public void logout(final User user) {
        userRepository.updateToken(null, user.getUsername());
    }
}
