package nl.app.loopsnelheid.account.presentation;

import lombok.RequiredArgsConstructor;
import nl.app.loopsnelheid.account.application.AuthService;
import nl.app.loopsnelheid.account.application.VerificationTokenService;
import nl.app.loopsnelheid.account.config.AccountEndpoints;
import nl.app.loopsnelheid.account.presentation.dto.RegisterDto;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(AccountEndpoints.REGISTER_PATH)
@RequiredArgsConstructor
public class RegisterController {
    private final AuthService authService;
    private final VerificationTokenService verificationTokenService;

    @PostMapping
    public void register(@Validated @RequestBody RegisterDto registerDto, HttpServletResponse response) {
        authService.userRegister(registerDto, registerDto.password);
        response.setStatus(HttpServletResponse.SC_CREATED);
    }

    @PostMapping(AccountEndpoints.VERIFY_DIGITAL_CODE_PATH)
    public void verifyEmailByDigitalCode(@PathVariable Long userId, @PathVariable String digitalCode)
    {
        verificationTokenService.verifyDigitalCode(userId, digitalCode);
    }

    @PostMapping(AccountEndpoints.VERIFY_TOKEN_PATH)
    public void verifyEmailByToken(@PathVariable Long userId, @PathVariable String token)
    {
        verificationTokenService.verifyToken(userId, token);
    }
}
