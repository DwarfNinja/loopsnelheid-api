package nl.app.loopsnelheid.meassurement.presentation.controller;

import nl.app.loopsnelheid.meassurement.application.MeasureService;
import nl.app.loopsnelheid.meassurement.domain.Measure;
import nl.app.loopsnelheid.meassurement.presentation.dto.MeasureDTO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/measures")
public class MeasureController
{
    private final MeasureService measureService;

    public MeasureController(MeasureService measureService)
    {
        this.measureService = measureService;
    }

    @GetMapping
    public List<MeasureDTO> getAll()
    {
        return this.measureService.getAll()
                .stream()
                .map(measure -> new MeasureDTO(measure.getId(), measure.getWalkingSpeed(), measure.getRegisteredAt()))
                .collect(Collectors.toList());
    }

    @PostMapping
    public List<MeasureDTO> createMany(@Validated @RequestBody List<MeasureDTO> measureDTOS)
    {
        List<Measure> measures = this.measureService.createMany(measureDTOS);

        return measures.stream()
                .map(measure -> new MeasureDTO(measure.getId(), measure.getWalkingSpeed(), measure.getRegisteredAt()))
                .collect(Collectors.toList());
    }
}
