package nl.app.loopsnelheid.security.presentation.controller;

import lombok.RequiredArgsConstructor;
import nl.app.loopsnelheid.security.application.DeleteRequestService;
import nl.app.loopsnelheid.security.application.UserService;
import nl.app.loopsnelheid.security.domain.User;
import nl.app.loopsnelheid.security.presentation.dto.ProfileDto;
import nl.app.loopsnelheid.security.presentation.dto.UserDto;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
public class AuthController
{
    private final UserService userService;
    private final DeleteRequestService deleteRequestService;

    @GetMapping("/profile")
    public ProfileDto profileUser()
    {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userService.loadUserByUsername(userDetails.getUsername());
        String pattern = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        return new ProfileDto(
                user.getId(),
                user.getEmail(),
                simpleDateFormat.format(user.getDateOfBirth()),
                user.getSex().toString(),
                user.isResearchCandidate(),
                deleteRequestService.hasOpenDeleteRequest(user),
                user.getLength(),
                user.getWeight(),
                user.getRoles().stream().map(role -> role.getName().toString()).collect(Collectors.toSet())
        );
    }
}