package nl.app.loopsnelheid.privacy.application.encoder;

import lombok.RequiredArgsConstructor;
import nl.app.loopsnelheid.measurement.domain.DefaultMeasure;
import nl.app.loopsnelheid.measurement.domain.Measure;
import nl.app.loopsnelheid.measurement.domain.MeasureStatistic;
import nl.app.loopsnelheid.privacy.domain.DataRequest;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class PrivacyPdfEncoder implements PdfEncoder
{
    private final DataRequest dataRequest;
    private final List<MeasureStatistic> measureStatistics;

    private final DefaultMeasure defaultMeasure;

    @Override
    public Map<String, Object> generate()
    {
        String pattern = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        Map<String, Object> variables = new HashMap<>();


        // General user information
        Map<String, Object> generalInformation = new HashMap<>();

        generalInformation.put("email", dataRequest.getEmail());
        generalInformation.put("birth_date", simpleDateFormat.format(dataRequest.getDateOfBirth()));
        generalInformation.put("sex", dataRequest.getSex().toString().equals("MALE") ? "Man" : "Vrouw");
        generalInformation.put("length", dataRequest.getLength());
        generalInformation.put("weight", dataRequest.getWeight());
        generalInformation.put("is_research_candidate", dataRequest.isResearchCandidate() ? "Ja" : "Nee");

        // Average measures last year
        Map<String, Double> averageDailyMeasuresLastYear = new HashMap<>();

        // Average measures in periods
        Map<String, Object> averageMeasuresPeriods = new HashMap<>();

        for (MeasureStatistic measureStatistic : measureStatistics)
        {
            Map<String, Object> periodStatistic = new HashMap<>();
            periodStatistic.put("average_speed", measureStatistic.getAverageWalkingSpeed());
            periodStatistic.put("amount_of_measures", Math.round(measureStatistic.getAmountOfMeasures()*100)/100);

            averageMeasuresPeriods.put(
                    measureStatistic.getMeasureStatisticType().toString().toLowerCase(),
                    periodStatistic
            );

            if (measureStatistic.getMeasureStatisticType().toString().equals("YEAR"))
            {
                for (Map.Entry<LocalDate, Double> day : measureStatistic.getAverageWalkingSpeedEachDay().entrySet())
                {
                    averageDailyMeasuresLastYear.put(day.getKey().format(formatDate), day.getValue());
                }
            }
        }

        // Setup
        variables.put("user", generalInformation);
        variables.put("measures_period", averageMeasuresPeriods);
        variables.put("daily_measures_last_year", averageDailyMeasuresLastYear);
        variables.put("minimum_speed", defaultMeasure.getSpeed());

        return variables;
    }
}
