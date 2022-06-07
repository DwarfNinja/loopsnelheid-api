package nl.app.loopsnelheid.security.presentation.controller;

import lombok.RequiredArgsConstructor;
import nl.app.loopsnelheid.security.application.DeviceService;
import nl.app.loopsnelheid.security.application.LoginService;
import nl.app.loopsnelheid.security.application.UserService;
import nl.app.loopsnelheid.security.domain.Device;
import nl.app.loopsnelheid.security.domain.JwtResponse;
import nl.app.loopsnelheid.security.domain.User;
import nl.app.loopsnelheid.security.presentation.dto.DeviceDto;
import nl.app.loopsnelheid.security.presentation.dto.JwtResponseDto;
import nl.app.loopsnelheid.security.presentation.dto.LoginDto;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LoginController
{
    private final LoginService loginService;
    private final UserService userService;

    private final DeviceService deviceService;

    @PostMapping("/login")
    public JwtResponseDto authenticateUser(@Valid @RequestBody LoginDto loginDto)
    {
        JwtResponse jwtResponse = loginService.authenticateUser(loginDto.email, loginDto.password);
        User authenticatedUser = userService.loadUserByUsername(loginService.getUserDetails().getUsername());
        Device device = deviceService.createDevice(authenticatedUser);

        return new JwtResponseDto(
                jwtResponse.getJwt(),
                jwtResponse.getEmail(),
                jwtResponse.getRoles(),
                new DeviceDto(device.getSession(), device.getEDevice().toString())
        );
    }


        return new JwtResponseDto(jwtResponse.getJwt(), jwtResponse.getEmail(), jwtResponse.getRoles());
    }
}