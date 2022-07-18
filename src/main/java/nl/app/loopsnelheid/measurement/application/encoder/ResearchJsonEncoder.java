package nl.app.loopsnelheid.measurement.application.encoder;

import lombok.RequiredArgsConstructor;
import nl.app.loopsnelheid.measurement.domain.ResearchData;
import nl.app.loopsnelheid.measurement.domain.ResearchDataCandidate;
import nl.app.loopsnelheid.measurement.domain.ResearchDataCandidateMeasure;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ResearchJsonEncoder implements Encoder
{
    private final ResearchData data;
    private JSONObject wrapper;

    @Override
    public void encode()
    {
        String pattern = "yyyy.mm.dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        JSONObject wrapper = new JSONObject();

        wrapper.put("aanvrager", data.getExportedBy());
        wrapper.put("aanmaak_datum_bestand", simpleDateFormat.format(data.getExportedOn()));

        JSONArray candidates = new JSONArray();

        for (ResearchDataCandidate candidate : data.getCandidates())
        {
            JSONObject candidateWrapper = new JSONObject();
            candidateWrapper.put("gebruiker_id", candidate.getId());
            candidateWrapper.put("geslacht", candidate.getSex().toString().equals("MALE") ? 1 : 2);
            candidateWrapper.put("geboorte_jaar", candidate.getBirthYear());
            candidateWrapper.put("lengte", candidate.getLength());
            candidateWrapper.put("gewicht", candidate.getWeight());

            JSONArray measures = new JSONArray();

            for (ResearchDataCandidateMeasure measure : candidate.getMeasures())
            {
                DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("yyyy.MM.dd");
                DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("HH:mm:ss");

                JSONObject measureWrapper = new JSONObject();
                measureWrapper.put("meting_id", measure.getId());
                measureWrapper.put("datum_meting", measure.getRegisteredAt().format(formatDate));
                measureWrapper.put("tijd_meting", measure.getRegisteredAt().format(formatTime));
                measureWrapper.put("gemiddelde_snelheid", measure.getWalkingSpeed());

                measures.add(measureWrapper);
            }

            candidateWrapper.put("metingen", measures);
            candidates.add(candidateWrapper);
        }

        wrapper.put("gebruiker", candidates);

        this.wrapper = wrapper;
    }

    public JSONObject getWrapper()
    {
        return wrapper;
    }
}
