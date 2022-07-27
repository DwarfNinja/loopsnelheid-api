package nl.app.loopsnelheid.security.application.listener;

import lombok.RequiredArgsConstructor;
import nl.app.loopsnelheid.security.application.ResetEmailVerificationService;
import nl.app.loopsnelheid.security.application.util.TokenGenerator;
import nl.app.loopsnelheid.security.config.AccountEndpoints;
import nl.app.loopsnelheid.security.domain.ResetEmailVerification;
import nl.app.loopsnelheid.security.domain.User;
import nl.app.loopsnelheid.security.domain.VerificationToken;
import nl.app.loopsnelheid.security.domain.event.OnEmailChangeEvent;
import nl.app.loopsnelheid.security.domain.event.OnRegistrationCompleteEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class EmailChangedListener implements ApplicationListener<OnEmailChangeEvent>
{
    private static final Logger logger = LoggerFactory.getLogger(EmailChangedListener.class);
    private final ResetEmailVerificationService resetEmailVerificationService;
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Value("${mail.api.url}")
    private String mailApiUrl;

    @Override
    public void onApplicationEvent(OnEmailChangeEvent onEmailChangeEvent)
    {
        this.confirmEmailChange(onEmailChangeEvent);
    }

    private void confirmEmailChange(OnEmailChangeEvent event)
    {
        ResetEmailVerification resetEmailVerification = event.getResetEmailVerification();
        User user = resetEmailVerification.getUser();

        sendConfirmationEmail(user.getId(), resetEmailVerification.getNewEmail(), resetEmailVerification.getToken());
    }

    private String generateMailApiUrl(Long userId, String token)
    {
        String verifyTokenPath = AccountEndpoints.RESET_EMAIL_VERIFICATION_PATH
                .replace("{userId}", userId.toString())
                .replace("{token}", token);

        return mailApiUrl + verifyTokenPath;
    }

    private void sendConfirmationEmail(Long userId, String email, String token)
    {
        Context context = new Context();
        context.setVariable("token", token);
        context.setVariable("mailApiUrl", generateMailApiUrl(userId, token));

        String process = templateEngine.process("reset_email", context);
        javax.mail.internet.MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        try
        {
            helper.setSubject("Bevestig uw e-mailadres ");
            helper.setText(process, true);
            helper.setTo(email);
            javaMailSender.send(mimeMessage);
        }
        catch(MessagingException messagingException)
        {
            logger.error(messagingException.getMessage());
        }
    }
}
