package pl.shonsu.shop.passwordresettoken.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.shonsu.shop.common.mail.EmailClientService;
import pl.shonsu.shop.passwordresettoken.model.EmailObject;
import pl.shonsu.shop.passwordresettoken.model.PasswordResetRequest;
import pl.shonsu.shop.passwordresettoken.model.PasswordResetToken;
import pl.shonsu.shop.passwordresettoken.model.PasswordResetTokenId;
import pl.shonsu.shop.passwordresettoken.repository.PasswordResetTokenRepository;
import pl.shonsu.shop.security.model.User;
import pl.shonsu.shop.security.repository.UserRepository;

import java.time.LocalDateTime;

import static pl.shonsu.shop.passwordresettoken.utils.TokenUtils.generateToken;
import static pl.shonsu.shop.passwordresettoken.utils.TokenUtils.hashToken;

@Service
@RequiredArgsConstructor
public class PasswordResetTokenService {

    private final UserRepository userRepository;
    private final EmailClientService emailClientService;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    @Value("${app.serviceAddress}")
    private String serviceAddress;

    public void sendLostPasswordLink(EmailObject email) {

        User user = userRepository.findByUsername(email.getEmail()).orElseThrow();

        String token = generateToken();
        String hashedToken = hashToken(token);
        PasswordResetToken passwordResetToken = new PasswordResetToken(new PasswordResetTokenId(user.getId(), hashedToken), LocalDateTime.now().plusMinutes(10), false);
        passwordResetTokenRepository.save(passwordResetToken);
        emailClientService.getInstance().send(email.getEmail(), "Reset hasła", createMessage(createLink(token)));
    }

    @Transactional
    public void resetPassword(PasswordResetRequest passwordResetRequest) {
        PasswordResetToken prt = passwordResetTokenRepository.findByIdToken(hashToken(passwordResetRequest.getToken())).orElseThrow();
        if (!passwordResetRequest.getPassword()
                .equals(passwordResetRequest.getRepeatedPassword())
                || !userRepository.existsById(prt.getId().getUserId())
                || !LocalDateTime.now().isBefore(prt.getTokenExpiry())
        ) {
            throw new IllegalArgumentException();
        }
        String password = "{bcrypt}" + new BCryptPasswordEncoder().encode(passwordResetRequest.getPassword());
        userRepository.setPassword(prt.getId().getUserId(), password);
    }

    private String createMessage(String link) {
        return "Wygenerowaliśmy dla Ciebie link do zmiany hasła" +
                "\n\nKliknij link, żeby zresetować hasło: " +
                "\n" + link +
                "\n\nDziękujemy.";
    }

    private String createLink(String token) {
        return serviceAddress + "/resetPassword?token=" + token;
    }
}
