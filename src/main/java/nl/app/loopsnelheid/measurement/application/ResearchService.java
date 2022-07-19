package nl.app.loopsnelheid.measurement.application;

import lombok.RequiredArgsConstructor;
import nl.app.loopsnelheid.measurement.application.encoder.ResearchJsonEncoder;
import nl.app.loopsnelheid.measurement.application.encoder.ResearchXmlEncoder;
import nl.app.loopsnelheid.measurement.application.handler.FileXmlHandler;
import nl.app.loopsnelheid.measurement.domain.Measure;
import nl.app.loopsnelheid.measurement.domain.ResearchData;
import nl.app.loopsnelheid.measurement.domain.ResearchDataCandidate;
import nl.app.loopsnelheid.measurement.domain.ResearchDataCandidateMeasure;
import nl.app.loopsnelheid.measurement.domain.event.OnResearchDataRequestCompleteEvent;
import nl.app.loopsnelheid.measurement.application.handler.ArchiveHandler;
import nl.app.loopsnelheid.measurement.application.handler.FileJsonHandler;
import nl.app.loopsnelheid.security.domain.User;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ResearchService
{
    private final ApplicationEventPublisher eventPublisher;

    public ResearchData initialResearchData(String exportedBy, List<User> researchCandidates)
    {
        List<ResearchDataCandidate> researchDataCandidates = new ArrayList<>();
        ResearchData researchData = new ResearchData(
                exportedBy,
                Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant())
        );

        for (User candidate : researchCandidates)
        {
            List<ResearchDataCandidateMeasure> researchDataCandidateMeasures = new ArrayList<>();

            ResearchDataCandidate researchDataCandidate = new ResearchDataCandidate(
                    candidate.getId(),
                    candidate.getSex(),
                    candidate.getBirthYear(),
                    candidate.getLength(),
                    candidate.getWeight()
            );

            for (Measure measure : candidate.getMeasures())
            {
                researchDataCandidateMeasures.add(new ResearchDataCandidateMeasure(
                        measure.getId(),
                        measure.getRegisteredAt(),
                        measure.getWalkingSpeed()
                ));
            }

            researchDataCandidate.setMeasures(researchDataCandidateMeasures);
            researchDataCandidates.add(researchDataCandidate);
        }

        researchData.setCandidates(researchDataCandidates);

        return researchData;
    }

    @Transactional
    public void handleRequest(ResearchData researchData)
    {
        Map<String, File> processedFiles = new HashMap<>();

        // Generating JSON file
        ResearchJsonEncoder researchJsonEncoder = new ResearchJsonEncoder(researchData);
        FileJsonHandler fileJsonHandler = new FileJsonHandler(researchJsonEncoder);
        fileJsonHandler.handle();
        processedFiles.put("onderzoeksgegevens.json", fileJsonHandler.getFile());

        // Generating XML file
        ResearchXmlEncoder researchXmlEncoder = new ResearchXmlEncoder(researchData);
        FileXmlHandler fileXmlHandler = new FileXmlHandler(researchXmlEncoder);
        fileXmlHandler.handle();
        processedFiles.put("onderzoeksgegevens.xml", fileXmlHandler.getFile());

        // Generate ZIP archive
        ArchiveHandler archiveHandler = new ArchiveHandler(processedFiles);
        archiveHandler.handle();

        String archivePath = archiveHandler.getPath();

        // Remove files
        fileJsonHandler.removeFile();
        fileXmlHandler.removeFile();

        eventPublisher.publishEvent(new OnResearchDataRequestCompleteEvent(archivePath, researchData));
    }
}
