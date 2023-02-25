package pl.shonsu.shop.passwordresettoken.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.shonsu.shop.passwordresettoken.model.EmailObject;
import pl.shonsu.shop.passwordresettoken.model.PasswordResetRequest;
import pl.shonsu.shop.passwordresettoken.service.PasswordResetTokenService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class LostPasswordController {

    private final PasswordResetTokenService passwordResetTokenService;
    @PostMapping("/resetPassword")
    void passwordResetLinkRequest(@RequestBody EmailObject emailObject){
        passwordResetTokenService.sendLostPasswordLink(emailObject);
    }

    @PatchMapping("/resetPassword")
    void resetPassword(@RequestBody PasswordResetRequest passwordResetRequest){
        passwordResetTokenService.resetPassword(passwordResetRequest);
    }
}
