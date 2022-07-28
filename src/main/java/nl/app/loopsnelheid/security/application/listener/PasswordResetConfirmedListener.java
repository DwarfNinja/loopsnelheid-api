package nl.app.loopsnelheid.security.application.listener;

import lombok.RequiredArgsConstructor;
import nl.app.loopsnelheid.security.domain.User;
import nl.app.loopsnelheid.security.domain.event.OnResetPasswordCompleteEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;

@Component
@RequiredArgsConstructor
public class PasswordResetConfirmedListener implements ApplicationListener<OnResetPasswordCompleteEvent>
{
    private static final Logger logger = LoggerFactory.getLogger(PasswordResetConfirmedListener.class);
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;


    @Override
    public void onApplicationEvent(OnResetPasswordCompleteEvent onResetPasswordCompleteEvent)
    {
        this.sendConfirmationEmail(onResetPasswordCompleteEvent);
    }

    private void sendConfirmationEmail(OnResetPasswordCompleteEvent event)
    {
        User user = event.getResetPasswordVerification().getUser();

        Context context = new Context();
        context.setVariable("password", event.getPassword());

        String process = templateEngine.process("successfully_reset_password", context);
        javax.mail.internet.MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        try
        {
            helper.setSubject("Uw wachtwoord is succesvol gewijzigd");
            helper.setText(process, true);
            helper.setTo(user.getEmail());
            javaMailSender.send(mimeMessage);
        }
        catch(MessagingException messagingException)
        {
            logger.error(messagingException.getMessage());
        }
    }
}
