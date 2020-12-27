package in.b2k.repository;

import in.b2k.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    User findByUsername(String firstName);

    Optional<User> findByToken(String token);

    Optional<User> findByUsernameAndPassword(String username, String password);

    @Modifying
    @Query("update User u set u.token = :token where u.username = :username")
    int updateToken(@Param("token") String token, @Param("username") String username);

    @Modifying
    @Query("update User u set u.token = :token, u.enabled = :enabled where u.id = :id")
    int updateTokenById(@Param("token") String token, @Param("enabled") Boolean enabled, @Param("id") UUID id);

    @Modifying
    @Query("update User u set u.token = null, u.lastLogin = CURRENT_TIMESTAMP where u.username = :username")
    int logout(@Param("username") String username);
}
