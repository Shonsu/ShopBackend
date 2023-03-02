package pl.shonsu.shop.passwordresettoken.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.shonsu.shop.passwordresettoken.model.PasswordResetToken;
import pl.shonsu.shop.passwordresettoken.model.PasswordResetTokenId;
import pl.shonsu.shop.passwordresettoken.repository.PasswordResetTokenRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PasswordResetTokenCleanupService {
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    @Transactional
    @Scheduled(cron = "${app.passwordResetToken.cleanup.expression}")
    void cleanupExpiredTokens() {
        List<PasswordResetToken> prt = passwordResetTokenRepository.findByTokenExpiryLessThan(LocalDateTime.now());
        List<PasswordResetTokenId> ids = prt.stream().map(PasswordResetToken::getId).toList();
        if (!ids.isEmpty()) {
            prt.forEach(passwordResetTokenId -> log.info("token: " + passwordResetTokenId.getId().getToken() + " expiry: " + passwordResetTokenId.getTokenExpiry()));
            passwordResetTokenRepository.deleteAllByIdIn(ids);
        }
    }
}
