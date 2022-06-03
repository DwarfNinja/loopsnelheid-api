package nl.app.loopsnelheid.security.presentation.controller;

import lombok.RequiredArgsConstructor;
import nl.app.loopsnelheid.security.application.LoginService;
import nl.app.loopsnelheid.security.domain.JwtResponse;
import nl.app.loopsnelheid.security.presentation.dto.JwtResponseDto;
import nl.app.loopsnelheid.security.presentation.dto.LoginDto;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LoginController
{
    private final LoginService loginService;

    @PostMapping("/login")
    public JwtResponseDto authenticateUser(@Valid @RequestBody LoginDto loginDto)
    {
        JwtResponse jwtResponse = loginService.authenticateUser(loginDto.email, loginDto.password);

        return new JwtResponseDto(jwtResponse.getJwt(), jwtResponse.getEmail(), jwtResponse.getRoles());
    }
}