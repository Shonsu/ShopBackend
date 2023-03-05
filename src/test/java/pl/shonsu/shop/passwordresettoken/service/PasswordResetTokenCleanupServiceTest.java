package pl.shonsu.shop.passwordresettoken.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.shonsu.shop.passwordresettoken.model.PasswordResetToken;
import pl.shonsu.shop.passwordresettoken.model.PasswordResetTokenId;
import pl.shonsu.shop.passwordresettoken.repository.PasswordResetTokenRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.shonsu.shop.passwordresettoken.utils.TokenUtils.generateToken;
import static pl.shonsu.shop.passwordresettoken.utils.TokenUtils.hashToken;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class PasswordResetTokenCleanupServiceTest {

    @Autowired
    PasswordResetTokenCleanupService cleanupService;

    @Autowired
    PasswordResetTokenRepository tokenRepository;

    @Test
    void shouldCleanupExpiredTokens() {
        //given
        createPasswordResetTokens();
        //when
        cleanupService.cleanupExpiredTokens();
        List<PasswordResetToken> resetTokens = tokenRepository.findAll();
        //then
        assertEquals(1, resetTokens.size());
    }

    private void createPasswordResetTokens() {

        List<PasswordResetToken> passwordResetTokens = new ArrayList<>();
        String token = generateToken();
        String hashedToken = hashToken(token);

        passwordResetTokens.add(new PasswordResetToken(new PasswordResetTokenId(1L, hashedToken), LocalDateTime.now().minusDays(1), false));
        token = generateToken();
        hashedToken = hashToken(token);
        passwordResetTokens.add(new PasswordResetToken(new PasswordResetTokenId(2L, hashedToken), LocalDateTime.now().minusMinutes(10), false));
        token = generateToken();
        hashedToken = hashToken(token);
        passwordResetTokens.add(new PasswordResetToken(new PasswordResetTokenId(3L, hashedToken), LocalDateTime.now().minusHours(5), false));
        token = generateToken();
        hashedToken = hashToken(token);
        passwordResetTokens.add(new PasswordResetToken(new PasswordResetTokenId(4L, hashedToken), LocalDateTime.now(), false));

        tokenRepository.saveAll(passwordResetTokens);
        tokenRepository.flush();
    }
}