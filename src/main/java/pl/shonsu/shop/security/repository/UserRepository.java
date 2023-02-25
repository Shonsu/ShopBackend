package pl.shonsu.shop.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.shonsu.shop.security.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);

    Optional<User> findByUuid(UUID username);

    @Modifying
    @Query("update User u set u.password = :password where u.id = :userId")
    void setPassword(@Param("userId") Long userId, @Param("password") String password);
}
