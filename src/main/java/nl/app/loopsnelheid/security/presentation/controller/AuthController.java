package nl.app.loopsnelheid.security.presentation.controller;

import lombok.RequiredArgsConstructor;
import nl.app.loopsnelheid.security.application.DeleteRequestJobService;
import nl.app.loopsnelheid.security.application.DeleteRequestService;
import nl.app.loopsnelheid.security.application.UserService;
import nl.app.loopsnelheid.security.domain.DeleteRequest;
import nl.app.loopsnelheid.security.domain.User;
import nl.app.loopsnelheid.security.presentation.dto.DeleteRequestDto;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController
{
    @PostMapping("/profile")
    public UserDetails profileUser()
    {
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}