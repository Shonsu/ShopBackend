package pl.shonsu.shop.passwordresettoken.model;

import lombok.Getter;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
@Getter
@Embeddable
public class PasswordResetTokenId implements Serializable {
    private Long userId;
    private String token;

    public PasswordResetTokenId() {
    }

    public PasswordResetTokenId(Long userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PasswordResetTokenId that = (PasswordResetTokenId) o;
        return userId.equals(that.userId) && token.equals(that.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, token);
    }
}
