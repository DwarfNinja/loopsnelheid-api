package nl.app.loopsnelheid.security.presentation.controller;

import lombok.RequiredArgsConstructor;
import nl.app.loopsnelheid.security.application.DeleteRequestJobService;
import nl.app.loopsnelheid.security.application.DeleteRequestService;
import nl.app.loopsnelheid.security.application.UserService;
import nl.app.loopsnelheid.security.domain.DeleteRequest;
import nl.app.loopsnelheid.security.domain.User;
import nl.app.loopsnelheid.security.domain.exception.DeleteRequestNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController
{
    private final UserService userService;
    private final DeleteRequestService deleteRequestService;
    private final DeleteRequestJobService deleteRequestJobService;

    @PostMapping("/profile")
    public UserDetails profileUser()
    {
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @PostMapping("/delete")
    public void deleteProfile()
    {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User authenticatedUser = userService.loadUserByUsername(userDetails.getUsername());

        DeleteRequest deleteRequest = deleteRequestService.initDeleteRequest(authenticatedUser);
        deleteRequestJobService.initJob(deleteRequest);
    }

    @PatchMapping("/delete")
    public void revokeDeleteProfile()
    {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User authenticatedUser = userService.loadUserByUsername(userDetails.getUsername());

        DeleteRequest deletedRequest = deleteRequestService.getDeleteRequestByUser(authenticatedUser);
        deleteRequestJobService.deleteJob(deletedRequest);
        deleteRequestService.revokeDeleteRequest(deletedRequest);
    }
}