package pl.shonsu.shop.passwordresettoken.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Table(name = "password_reset_tokens")
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetToken {
    @EmbeddedId
    private PasswordResetTokenId id;
    private LocalDateTime tokenExpiry;
    private boolean tokenUsed;
}
