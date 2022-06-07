package nl.app.loopsnelheid.meassurement.presentation.controller;

import lombok.RequiredArgsConstructor;
import nl.app.loopsnelheid.meassurement.application.DefaultMeasureService;
import nl.app.loopsnelheid.meassurement.application.MeasureService;
import nl.app.loopsnelheid.meassurement.domain.Measure;
import nl.app.loopsnelheid.meassurement.presentation.dto.DefaultMeasureDto;
import nl.app.loopsnelheid.meassurement.presentation.dto.DefaultMeasureStatisticDto;
import nl.app.loopsnelheid.meassurement.presentation.dto.MeasureDto;
import nl.app.loopsnelheid.security.application.DeviceService;
import nl.app.loopsnelheid.security.application.UserService;
import nl.app.loopsnelheid.security.domain.Device;
import nl.app.loopsnelheid.security.domain.EDevice;
import nl.app.loopsnelheid.security.domain.Sex;
import nl.app.loopsnelheid.security.domain.User;
import nl.app.loopsnelheid.meassurement.domain.exception.UnauthorizedMeasureDeviceException;
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

    private final DefaultMeasureService defaultMeasureService;
    private final UserService userService;

    private final DeviceService deviceService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<MeasureDto> getAll()
    {
        return measureService.getAllMeasures()
                .stream()
                .map(measure -> new MeasureDto(measure.getId(), measure.getWalkingSpeed(), measure.getRegisteredAt()))
                .collect(Collectors.toList());
    }

    @PostMapping
    public List<MeasureDto> createMany(@RequestHeader("session") String session, @Validated @RequestBody List<MeasureDto> measureDtos)
    {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User authenticatedUser = userService.loadUserByUsername(userDetails.getUsername());

        Device device = deviceService.getDeviceBySession(session);

        if (device.getEDevice().equals(EDevice.READING_DEVICE))
        {
            throw new UnauthorizedMeasureDeviceException();
        }

        List<Measure> measures = measureService.createManyMeasures(measureDtos, authenticatedUser);

        return measures.stream()
                .map(measure -> new MeasureDto(measure.getId(), measure.getWalkingSpeed(), measure.getRegisteredAt()))
                .collect(Collectors.toList());
    }

    @GetMapping("/default")
    public DefaultMeasureStatisticDto getDefaultMeasures()
    {
        return new DefaultMeasureStatisticDto(
                defaultMeasureService.getDefaultMeasuresBySex(Sex.MALE).stream().map(
                        defaultMeasure -> new DefaultMeasureDto(defaultMeasure.getAge(), defaultMeasure.getSpeed())
                ).collect(Collectors.toList()),
                defaultMeasureService.getDefaultMeasuresBySex(Sex.FEMALE).stream().map(
                        defaultMeasure -> new DefaultMeasureDto(defaultMeasure.getAge(), defaultMeasure.getSpeed())
                ).collect(Collectors.toList())
        );
    }
}
