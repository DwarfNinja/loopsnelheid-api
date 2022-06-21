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
import java.util.List;
import java.util.stream.Collectors;

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
                new DeviceDto(device.getId(),device.getSession(), device.getEDevice().toString())
        );
    }

    @PostMapping("/logout")
    public void logoutUser(@RequestHeader("session") String session)
    {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User authenticatedUser = userService.loadUserByUsername(userDetails.getUsername());

        Device device = deviceService.getDeviceBySession(session);
        deviceService.revokeDevice(device, authenticatedUser);
    }

    @GetMapping("/devices")
    public List<DeviceDto> getDevices()
    {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User authenticatedUser = userService.loadUserByUsername(userDetails.getUsername());
        
        return  authenticatedUser.getDevices().stream()
                .map(device -> new DeviceDto(device.getId(), device.getSession(), device.getEDevice().toString()))
                .collect(Collectors.toList());
    }

    @PatchMapping("/devices/{session}")
    public void markDeviceAsMeasureDevice(@PathVariable String session)
    {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User authenticatedUser = userService.loadUserByUsername(userDetails.getUsername());

        Device device = deviceService.getDeviceBySession(session);

        deviceService.markDeviceAsMeasureDevice(device, authenticatedUser);
    }
}