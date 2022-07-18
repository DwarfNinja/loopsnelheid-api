package nl.app.loopsnelheid.measurement.presentation.dto;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class ResearchStatisticDto
{
    public Map<Integer, ResearchStatisticAgeDto> age;
}
