package nl.app.loopsnelheid.security.presentation.controller;

import lombok.RequiredArgsConstructor;
import nl.app.loopsnelheid.security.application.RegisterService;
import nl.app.loopsnelheid.security.application.VerificationTokenService;
import nl.app.loopsnelheid.security.config.AccountEndpoints;
import nl.app.loopsnelheid.security.presentation.dto.DetailsDto;
import nl.app.loopsnelheid.security.presentation.dto.RegisterDto;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
public class RegisterController
{
    private final RegisterService registerService;
    private final VerificationTokenService verificationTokenService;

    @PostMapping(AccountEndpoints.REGISTER_PATH)
    public DetailsDto register(@Validated @RequestBody RegisterDto registerDto)
    {
        Long id = registerService.registerUser(
                registerDto.id,
                registerDto.password,
                registerDto.email,
                registerDto.dateOfBirth,
                registerDto.sex,
                registerDto.researchPurposes,
                registerDto.termsAndConditions,
                registerDto.privacyStatement,
                registerDto.length,
                registerDto.weight
        );

        return new DetailsDto(id);
    }

    @PostMapping(AccountEndpoints.VERIFY_DIGITAL_CODE_PATH)
    public void verifyEmailByDigitalCode(@PathVariable Long userId, @PathVariable String digitalCode)
    {
        verificationTokenService.verifyDigitalCode(userId, digitalCode);
    }

    @GetMapping(AccountEndpoints.VERIFY_TOKEN_PATH)
    public void verifyEmailByToken(@PathVariable Long userId, @PathVariable String token, HttpServletResponse response) throws IOException
    {
        verificationTokenService.verifyToken(userId, token);
        response.sendRedirect("intent:#Intent;scheme=startci;package=com.example.loopsnelheidapp;end");
    }
}
