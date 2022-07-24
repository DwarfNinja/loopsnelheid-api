package nl.app.loopsnelheid.security.application.listener;

import lombok.RequiredArgsConstructor;
import nl.app.loopsnelheid.security.domain.User;
import nl.app.loopsnelheid.security.domain.event.OnLoginCompleteEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LoginListener implements ApplicationListener<OnLoginCompleteEvent>
{
    private static final Logger logger = LoggerFactory.getLogger(LoginListener.class);
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Override
    public void onApplicationEvent(OnLoginCompleteEvent onRegistrationCompleteEvent)
    {
        this.sendConfirmationEmail(onRegistrationCompleteEvent);
    }

    private void sendConfirmationEmail(OnLoginCompleteEvent onLoginCompleteEvent)
    {
        UserDetails user = onLoginCompleteEvent.getUserDetails();
        Context context = new Context();

        String process = templateEngine.process("successfully_login", context);
        javax.mail.internet.MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        try
        {
            helper.setSubject("Er is ingelogd op uw account");
            helper.setText(process, true);
            helper.setTo(user.getUsername());
            javaMailSender.send(mimeMessage);
        }
        catch(MessagingException messagingException)
        {
            logger.error(messagingException.getMessage());
        }
    }
}
