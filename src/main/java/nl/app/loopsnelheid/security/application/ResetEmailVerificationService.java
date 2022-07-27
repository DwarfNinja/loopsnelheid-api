package nl.app.loopsnelheid.security.application;

import lombok.RequiredArgsConstructor;
import nl.app.loopsnelheid.security.application.util.TokenGenerator;
import nl.app.loopsnelheid.security.data.ResetEmailVerificationRepository;
import nl.app.loopsnelheid.security.domain.ResetEmailVerification;
import nl.app.loopsnelheid.security.domain.User;
import nl.app.loopsnelheid.security.domain.event.OnEmailChangeCompleteEvent;
import nl.app.loopsnelheid.security.domain.exception.VerificationTokenExpiredException;
import nl.app.loopsnelheid.security.domain.exception.VerificationTokenNotFoundException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ResetEmailVerificationService
{
    private final ResetEmailVerificationRepository resetEmailVerificationRepository;
    private final UserService userService;
    private final ApplicationEventPublisher eventPublisher;


    public ResetEmailVerification createResetEmailVerification(User user, String newEmail)
    {
        String token = TokenGenerator.generateToken();
        LocalDateTime expiryDate = ResetEmailVerification.generateExpiryDate();

        return new ResetEmailVerification(
                token,
                expiryDate,
                newEmail,
                user
        );
    }

    public void saveResetEmailVerification(ResetEmailVerification resetEmailVerification)
    {
        resetEmailVerificationRepository.save(resetEmailVerification);
    }

    public void determineResetEmailVerification(ResetEmailVerification resetEmailVerification)
    {
        if (resetEmailVerification.isExpired() || resetEmailVerification.isVerified()) throw new VerificationTokenExpiredException();
    }

    public void verifyToken(Long userId, String token)
    {
        ResetEmailVerification resetEmailVerification = resetEmailVerificationRepository.findByTokenAndUserId(token, userId)
                .orElseThrow(VerificationTokenNotFoundException::new);

        determineResetEmailVerification(resetEmailVerification);
        resetEmailVerification.setVerificationDate(LocalDateTime.now());
        saveResetEmailVerification(resetEmailVerification);
        sendResetEmailVerificationCompleteNotification(userId, resetEmailVerification.getNewEmail());
    }

    private void sendResetEmailVerificationCompleteNotification(Long userId, String email)
    {
        User user = userService.updateEmailById(userId, email);

        eventPublisher.publishEvent(new OnEmailChangeCompleteEvent(user));
    }
}
