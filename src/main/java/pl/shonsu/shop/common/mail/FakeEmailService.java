package pl.shonsu.shop.common.mail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FakeEmailService implements EmailSender{
    @Override
    public void send(String to, String subject, String msg) {
        log.info("Email Send.");
        log.info("To: " + to);
        log.info("Subject: " + subject);
        log.info("Message: " + msg);
    }
}
