package nl.app.loopsnelheid.security.presentation.controller;

import lombok.RequiredArgsConstructor;
import nl.app.loopsnelheid.security.application.DeleteRequestJobService;
import nl.app.loopsnelheid.security.application.DeleteRequestService;
import nl.app.loopsnelheid.security.application.UserService;
import nl.app.loopsnelheid.security.domain.DeleteRequest;
import nl.app.loopsnelheid.security.domain.User;
import nl.app.loopsnelheid.security.presentation.dto.ChangePasswordDto;
import nl.app.loopsnelheid.security.presentation.dto.ChangePersonalInformationDto;
import nl.app.loopsnelheid.security.presentation.dto.DeleteRequestDto;
import nl.app.loopsnelheid.security.presentation.dto.UserDto;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class ProfileController
{
    private final UserService userService;
    private final DeleteRequestService deleteRequestService;
    private final DeleteRequestJobService deleteRequestJobService;

    @GetMapping("/profile")
    public UserDetails profileUser()
    {
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @PostMapping("/profile/delete")
    public void deleteProfile()
    {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User authenticatedUser = userService.loadUserByUsername(userDetails.getUsername());

        DeleteRequest deleteRequest = deleteRequestService.initDeleteRequest(authenticatedUser);
        deleteRequestJobService.initJob(deleteRequest);
    }

    @DeleteMapping("/profile/delete")
    public DeleteRequestDto revokeDeleteProfile()
    {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User authenticatedUser = userService.loadUserByUsername(userDetails.getUsername());

        DeleteRequest deletedRequest = deleteRequestService.getDeleteRequestByUser(authenticatedUser);
        deleteRequestJobService.deleteJob(deletedRequest);

        return new DeleteRequestDto(deletedRequest.getJobId());
    }

    @PostMapping("/profile/password")
    public void updatePassword(@Validated @RequestBody ChangePasswordDto changePasswordDto)
    {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User authenticatedUser = userService.loadUserByUsername(userDetails.getUsername());

        userService.updatePasswordById(
                authenticatedUser.getId(),
                changePasswordDto.oldPassword,
                changePasswordDto.newPassword
        );
    }

    @PostMapping("/profile/personal")
    public ChangePersonalInformationDto updatePersonalInformation(@Validated @RequestBody ChangePersonalInformationDto dto)
    {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User authenticatedUser = userService.loadUserByUsername(userDetails.getUsername());

        User updatedUser = userService.updatePersonalInformationById(
                authenticatedUser.getId(),
                dto.dateOfBirth,
                dto.sex,
                dto.length,
                dto.weight,
                dto.isResearchCandidate
        );

        return new ChangePersonalInformationDto(
                updatedUser.getDateOfBirth(),
                updatedUser.getSex().toString(),
                updatedUser.isResearchCandidate(),
                updatedUser.getLength(),
                updatedUser.getWeight()
        );
    }
}