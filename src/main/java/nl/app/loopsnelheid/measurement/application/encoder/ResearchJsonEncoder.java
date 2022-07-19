package nl.app.loopsnelheid.measurement.application.encoder;

import lombok.RequiredArgsConstructor;
import nl.app.loopsnelheid.measurement.domain.ResearchData;
import nl.app.loopsnelheid.measurement.domain.ResearchDataCandidate;
import nl.app.loopsnelheid.measurement.domain.ResearchDataCandidateMeasure;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class ResearchJsonEncoder implements JsonEncoder
{
    private final ResearchData data;
    private JSONObject wrapper;

    @Override
    public void encode()
    {
        String pattern = "yyyy.mm.dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        Map<String, Object> wrapperMap = new HashMap<>();
        wrapperMap.put("aanvrager", data.getExportedBy());
        wrapperMap.put("aanmaak_datum_bestand", simpleDateFormat.format(data.getExportedOn()));
        wrapperMap.put("specificatie_nr", "Specificatie80");

        JSONArray candidates = new JSONArray();

        for (ResearchDataCandidate candidate : data.getCandidates())
        {
            Map<String, Object> candidateWrapperMap = new HashMap<>();
            candidateWrapperMap.put("gebruiker_id", candidate.getId());
            candidateWrapperMap.put("geslacht", candidate.getSex().toString().equals("MALE") ? 1 : 2);
            candidateWrapperMap.put("geboortejaar", candidate.getBirthYear());
            candidateWrapperMap.put("lengte", candidate.getLength());
            candidateWrapperMap.put("gewicht", candidate.getWeight());

            JSONArray measures = new JSONArray();

            for (ResearchDataCandidateMeasure measure : candidate.getMeasures())
            {
                DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("yyyy.MM.dd");
                DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("HH:mm:ss");

                Map<String, Object> measureWrapperMap = new HashMap<>();
                measureWrapperMap.put("meting_id", measure.getId());
                measureWrapperMap.put("datum_meting", measure.getRegisteredAt().format(formatDate));
                measureWrapperMap.put("tijd_meting", measure.getRegisteredAt().format(formatTime));
                measureWrapperMap.put("gemiddelde_snelheid", measure.getWalkingSpeed());

                measures.add(new JSONObject(measureWrapperMap));
            }

            candidateWrapperMap.put("metingen", measures);
            candidates.add(new JSONObject(candidateWrapperMap));
        }

        wrapperMap.put("gebruiker", candidates);

        this.wrapper = new JSONObject(wrapperMap);
    }

    public JSONObject getWrapper()
    {
        return wrapper;
    }
}
