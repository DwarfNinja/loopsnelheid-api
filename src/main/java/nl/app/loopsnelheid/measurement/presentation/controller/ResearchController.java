package nl.app.loopsnelheid.measurement.presentation.controller;

import lombok.RequiredArgsConstructor;
import nl.app.loopsnelheid.measurement.application.ResearchDataRequestJobService;
import nl.app.loopsnelheid.measurement.application.ResearchService;
import nl.app.loopsnelheid.measurement.domain.ResearchData;
import nl.app.loopsnelheid.measurement.presentation.dto.ResearchDataRequestDto;
import nl.app.loopsnelheid.security.application.UserService;
import nl.app.loopsnelheid.security.domain.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/research")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
public class ResearchController
{
    private final UserService userService;
    private final ResearchService researchService;
    private final ResearchDataRequestJobService researchDataRequestJobService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('RESEARCHER')")
    public void requestResearchData(@Validated @RequestBody ResearchDataRequestDto researchDataRequestDto)
    {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User authenticatedUser = userService.loadUserByUsername(userDetails.getUsername());

        ResearchData researchData = researchService.initialResearchData(
                authenticatedUser.getEmail(),
                userService.getResearchCandidates()
        );

        researchDataRequestJobService.setPreferences(
                researchDataRequestDto.withJson,
                researchDataRequestDto.withXml,
                researchDataRequestDto.withCsv
        );
        researchDataRequestJobService.initJob(researchData);
    }
}
