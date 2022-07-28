package nl.app.loopsnelheid.security.presentation.controller;

import lombok.RequiredArgsConstructor;
import nl.app.loopsnelheid.security.application.ResetPasswordVerificationService;
import nl.app.loopsnelheid.security.application.UserService;
import nl.app.loopsnelheid.security.config.AccountEndpoints;
import nl.app.loopsnelheid.security.domain.ResetPasswordVerification;
import nl.app.loopsnelheid.security.domain.User;
import nl.app.loopsnelheid.security.presentation.dto.ResetPasswordDto;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class ForgotPasswordController
{
    private final UserService userService;
    private final ResetPasswordVerificationService resetPasswordVerificationService;

    @PostMapping(AccountEndpoints.RESET_PASSWORD_PATH)
    public void resetPassword(@Validated @RequestBody ResetPasswordDto dto)
    {
        User user = userService.loadUserByUsername(dto.email);
        ResetPasswordVerification resetEmailVerification = resetPasswordVerificationService.createResetPasswordVerification(user);
        resetPasswordVerificationService.saveResetPasswordVerification(resetEmailVerification);
        userService.requestPasswordUpdateById(resetEmailVerification);
    }

    @GetMapping(AccountEndpoints.RESET_PASSWORD_VERIFICATION_PATH)
    public void verifyEmailByToken(@PathVariable Long userId, @PathVariable String token, HttpServletResponse response) throws IOException
    {
        resetPasswordVerificationService.verifyToken(userId, token);
        response.sendRedirect("intent:#Intent;scheme=startci;package=com.example.loopsnelheidapp;end");
    }
}
