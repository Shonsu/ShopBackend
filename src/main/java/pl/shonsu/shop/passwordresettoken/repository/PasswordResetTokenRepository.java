package pl.shonsu.shop.passwordresettoken.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import pl.shonsu.shop.passwordresettoken.model.PasswordResetToken;
import pl.shonsu.shop.passwordresettoken.model.PasswordResetTokenId;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, PasswordResetTokenId> {
    Optional<PasswordResetToken> findByIdToken(String hashToken);

    List<PasswordResetToken> findByTokenExpiryLessThan(LocalDateTime now);

    @Query("select id from PasswordResetToken")
    List<PasswordResetTokenId> findAllId();

    @Modifying
    @Query("delete from PasswordResetToken where id in (:ids)")
    void deleteAllByIdIn(List<PasswordResetTokenId> ids);
}
