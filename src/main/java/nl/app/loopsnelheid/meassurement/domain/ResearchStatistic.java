package nl.app.loopsnelheid.meassurement.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Map;

@RequiredArgsConstructor
@Getter
@Setter
public class ResearchStatistic
{
    private final Map<Integer, ResearchStatisticAge> researchStatisticAgeMap;
    private String email;
    private String path;
}
