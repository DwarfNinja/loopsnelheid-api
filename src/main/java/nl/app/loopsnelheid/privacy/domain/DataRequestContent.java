package nl.app.loopsnelheid.privacy.domain;

import nl.app.loopsnelheid.meassurement.domain.Measure;

import java.util.List;

public class DataRequestContent
{
    private final PersonalData personalData;
    private final List<Measure> measures;

    public DataRequestContent(PersonalData personalData, List<Measure> measures) {
        this.personalData = personalData;
        this.measures = measures;
    }

    public PersonalData getPersonalData() {
        return personalData;
    }

    public List<Measure> getMeasures() {
        return measures;
    }
}
