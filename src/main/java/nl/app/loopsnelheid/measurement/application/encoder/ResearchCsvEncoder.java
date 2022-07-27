package nl.app.loopsnelheid.measurement.application.encoder;

import lombok.RequiredArgsConstructor;
import nl.app.loopsnelheid.measurement.domain.ResearchData;
import nl.app.loopsnelheid.measurement.domain.ResearchDataCandidate;
import nl.app.loopsnelheid.measurement.domain.ResearchDataCandidateMeasure;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ResearchCsvEncoder implements CsvEncoder
{
    private final ResearchData researchData;
    private final String type;

    @Override
    public List<String[]> create()
    {
        String pattern = "yyyy.mm.dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("HH:mm:ss");

        List<String[]> candidates = new ArrayList<>();
        List<String[]> measures = new ArrayList<>();
        String[] candidatesHeader = {"Aanvrager", "AanmaakDatumBestand", "SpecificatieNr", "Gebruiker_ID", "Geslacht", "Geboortejaar", "Lengte", "Gewicht"};
        String[] measuresHeader = {"Aanvrager", "AanmaakDatumBestand", "SpecificatieNr", "Gebruiker_ID", "Meting_ID", "DatumMeting", "TijdMeting", "GemiddeldeSnelheid"};
        candidates.add(candidatesHeader);
        measures.add(measuresHeader);

        for (ResearchDataCandidate candidate : researchData.getCandidates())
        {
            candidates.add(new String[]{
                    researchData.getExportedBy(),
                    simpleDateFormat.format(researchData.getExportedOn()),
                    "Specificatie80",
                    candidate.getId().toString(),
                    candidate.getSex().toString().equals("MALE") ? "1" : "2",
                    candidate.getBirthYear(),
                    Integer.toString(candidate.getLength()),
                    Integer.toString(candidate.getWeight())
            });

            if (type.equals("Measures"))
            {
                for (ResearchDataCandidateMeasure measure : candidate.getMeasures())
                {
                    measures.add(new String[]{
                            researchData.getExportedBy(),
                            simpleDateFormat.format(researchData.getExportedOn()),
                            "Specificatie80",
                            candidate.getId().toString(),
                            measure.getId().toString(),
                            measure.getRegisteredAt().format(formatDate),
                            measure.getRegisteredAt().format(formatTime),
                            Double.toString(measure.getWalkingSpeed())
                    });
                }
            }
        }

        return type.equals("Candidates") ? candidates : measures;
    }
}
