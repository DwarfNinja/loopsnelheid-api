package nl.app.loopsnelheid.account.presentation;

import lombok.RequiredArgsConstructor;
import nl.app.loopsnelheid.account.application.AuthService;
import nl.app.loopsnelheid.account.config.AccountEndpoints;
import nl.app.loopsnelheid.account.presentation.dto.RegisterDto;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(AccountEndpoints.REGISTER_PATH)
@RequiredArgsConstructor
public class RegisterController {
    private final AuthService authService;

    @PostMapping
    public void register(@Validated @RequestBody RegisterDto registerDto, HttpServletResponse response) {
        authService.userRegister(registerDto, registerDto.password);
        response.setStatus(HttpServletResponse.SC_CREATED);
    }
}
