package nl.app.loopsnelheid.security.application.listener;

import lombok.RequiredArgsConstructor;
import nl.app.loopsnelheid.security.domain.User;
import nl.app.loopsnelheid.security.domain.event.OnPasswordChangeEvent;
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
public class PasswordChangedListener implements ApplicationListener<OnPasswordChangeEvent>
{
    private static final Logger logger = LoggerFactory.getLogger(PasswordChangedListener.class);
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Override
    public void onApplicationEvent(OnPasswordChangeEvent onPasswordChangeEvent)
    {
        this.sendConfirmationEmail(onPasswordChangeEvent);
    }

    private void sendConfirmationEmail(OnPasswordChangeEvent onPasswordChangeEvent)
    {
        User user = onPasswordChangeEvent.getUser();

        Context context = new Context();

        String process = templateEngine.process("successfully_changed_password", context);
        javax.mail.internet.MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        try
        {
            helper.setSubject("Het wachtwoord van uw account is gewijzigd");
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
