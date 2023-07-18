package nl.app.loopsnelheid.security.application;

import lombok.RequiredArgsConstructor;
import nl.app.loopsnelheid.security.application.util.TokenGenerator;
import nl.app.loopsnelheid.security.data.ResetPasswordVerificationRepository;
import nl.app.loopsnelheid.security.domain.ResetEmailVerification;
import nl.app.loopsnelheid.security.domain.ResetPasswordVerification;
import nl.app.loopsnelheid.security.domain.User;
import nl.app.loopsnelheid.security.domain.event.OnResetPasswordCompleteEvent;
import nl.app.loopsnelheid.security.domain.exception.VerificationTokenExpiredException;
import nl.app.loopsnelheid.security.domain.exception.VerificationTokenNotFoundException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ResetPasswordVerificationService
{
    private final ResetPasswordVerificationRepository resetPasswordVerificationRepository;
    private final UserService userService;
    private final ApplicationEventPublisher eventPublisher;


    public ResetPasswordVerification createResetPasswordVerification(User user)
    {
        String token = TokenGenerator.generateToken();
        LocalDateTime expiryDate = ResetEmailVerification.generateExpiryDate();

        return new ResetPasswordVerification(
                token,
                expiryDate,
                user
        );
    }

    public void saveResetPasswordVerification(ResetPasswordVerification resetPasswordVerification)
    {
        resetPasswordVerificationRepository.save(resetPasswordVerification);
    }

    public void determineResetPasswordVerification(ResetPasswordVerification resetPasswordVerification)
    {
        if (resetPasswordVerification.isExpired() || resetPasswordVerification.isVerified()) throw new VerificationTokenExpiredException();
    }

    public void verifyToken(Long userId, String token)
    {
        ResetPasswordVerification resetPasswordVerification = resetPasswordVerificationRepository.findByTokenAndUserId(token, userId)
                .orElseThrow(VerificationTokenNotFoundException::new);

        determineResetPasswordVerification(resetPasswordVerification);
        resetPasswordVerification.setVerificationDate(LocalDateTime.now());
        saveResetPasswordVerification(resetPasswordVerification);

        sendResetPasswordVerificationCompleteNotification(userId, resetPasswordVerification);
    }

    private void sendResetPasswordVerificationCompleteNotification(Long userId, ResetPasswordVerification resetPasswordVerification)
    {
        String password = userService.resetPasswordById(userId);

        eventPublisher.publishEvent(new OnResetPasswordCompleteEvent(resetPasswordVerification, password));
    }
}
