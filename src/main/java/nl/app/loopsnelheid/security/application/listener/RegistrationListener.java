package nl.app.loopsnelheid.security.application.listener;

import lombok.RequiredArgsConstructor;
import nl.app.loopsnelheid.security.application.VerificationTokenService;
import nl.app.loopsnelheid.security.application.util.TokenGenerator;
import nl.app.loopsnelheid.security.config.AccountEndpoints;
import nl.app.loopsnelheid.security.domain.User;
import nl.app.loopsnelheid.security.domain.VerificationToken;
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
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent>
{
    private static final Logger logger = LoggerFactory.getLogger(RegistrationListener.class);
    private final VerificationTokenService verificationTokenService;
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Value("${mail.api.url}")
    private String mailApiUrl;

    @Value("${spring.mail.from}")
    private String from;

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent onRegistrationCompleteEvent)
    {
        this.confirmRegistration(onRegistrationCompleteEvent);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event)
    {
        User user = event.getUser();
        String generatedToken = TokenGenerator.generateToken();
        List<Integer> generatedDigitalCodeList = TokenGenerator.generateDigitalCode(6);
        VerificationToken verificationToken = verificationTokenService.createVerificationToken(user, generatedToken, generatedDigitalCodeList);
        verificationTokenService.saveVerificationToken(verificationToken);

        sendConfirmationEmail(user.getId(), user.getEmail(), verificationToken.getToken(), verificationToken.getDigitalCode());
    }

    private String generateMailApiUrl(Long userId, String token)
    {
        String verifyTokenPath = AccountEndpoints.VERIFY_TOKEN_PATH
                .replace("{userId}", userId.toString())
                .replace("{token}", token);

        return mailApiUrl + verifyTokenPath;
    }

    private void sendConfirmationEmail(Long userId, String email, String token, String digitalCode)
    {
        Context context = new Context();
        context.setVariable("token", token);
        context.setVariable("digitalCode", digitalCode);
        context.setVariable("mailApiUrl", generateMailApiUrl(userId, token));

        String process = templateEngine.process("welcome", context);
        javax.mail.internet.MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        try
        {
            helper.setFrom(from);
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
