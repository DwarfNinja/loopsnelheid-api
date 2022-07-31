package nl.app.loopsnelheid.security.application.listener;

import lombok.RequiredArgsConstructor;
import nl.app.loopsnelheid.security.config.AccountEndpoints;
import nl.app.loopsnelheid.security.domain.ResetPasswordVerification;
import nl.app.loopsnelheid.security.domain.User;
import nl.app.loopsnelheid.security.domain.event.OnResetPasswordEvent;
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

@Component
@RequiredArgsConstructor
public class PasswordResetListener implements ApplicationListener<OnResetPasswordEvent>
{
    private static final Logger logger = LoggerFactory.getLogger(PasswordResetListener.class);
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Value("${mail.api.url}")
    private String mailApiUrl;

    @Value("${spring.mail.username}")
    private String from;

    @Override
    public void onApplicationEvent(OnResetPasswordEvent onResetPasswordEvent)
    {
        this.confirmEmailChange(onResetPasswordEvent);
    }

    private void confirmEmailChange(OnResetPasswordEvent event)
    {
        ResetPasswordVerification resetPasswordVerification = event.getResetPasswordVerification();
        User user = resetPasswordVerification.getUser();

        sendConfirmationEmail(user.getId(), user.getEmail(), resetPasswordVerification.getToken());
    }

    private String generateMailApiUrl(Long userId, String token)
    {
        String verifyTokenPath = AccountEndpoints.RESET_PASSWORD_VERIFICATION_PATH
                .replace("{userId}", userId.toString())
                .replace("{token}", token);

        return mailApiUrl + verifyTokenPath;
    }

    private void sendConfirmationEmail(Long userId, String email, String token)
    {
        Context context = new Context();
        context.setVariable("token", token);
        context.setVariable("mailApiUrl", generateMailApiUrl(userId, token));

        String process = templateEngine.process("reset_password", context);
        javax.mail.internet.MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        try
        {
            helper.setFrom(from);
            helper.setSubject("Bevestig uw nieuwe wachtwoord ");
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
