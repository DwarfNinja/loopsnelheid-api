package nl.app.loopsnelheid.meassurement.presentation.controller;

import lombok.RequiredArgsConstructor;
import nl.app.loopsnelheid.meassurement.application.MeasureService;
import nl.app.loopsnelheid.meassurement.domain.Measure;
import nl.app.loopsnelheid.meassurement.presentation.dto.MeasureDTO;
import nl.app.loopsnelheid.security.application.UserService;
import nl.app.loopsnelheid.security.domain.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/measures")
@RequiredArgsConstructor
public class MeasureController
{
    private final MeasureService measureService;
    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<MeasureDTO> getAll()
    {
        return measureService.getAllMeasures()
                .stream()
                .map(measure -> new MeasureDTO(measure.getId(), measure.getWalkingSpeed(), measure.getRegisteredAt()))
                .collect(Collectors.toList());
    }

    @PostMapping
    public List<MeasureDTO> createMany(@Validated @RequestBody List<MeasureDTO> measureDTOS)
    {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User authenticatedUser = userService.loadUserByUsername(userDetails.getUsername());

        List<Measure> measures = measureService.createManyMeasures(measureDTOS, authenticatedUser);

        return measures.stream()
                .map(measure -> new MeasureDTO(measure.getId(), measure.getWalkingSpeed(), measure.getRegisteredAt()))
                .collect(Collectors.toList());
    }
}
