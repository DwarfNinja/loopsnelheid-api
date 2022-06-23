package nl.app.loopsnelheid.security.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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