package nl.app.loopsnelheid.privacy.application;

import lombok.RequiredArgsConstructor;
import nl.app.loopsnelheid.privacy.application.handler.ArchiveHandler;
import nl.app.loopsnelheid.privacy.application.handler.DataRequestHandler;
import nl.app.loopsnelheid.privacy.application.handler.FileHandler;
import nl.app.loopsnelheid.privacy.data.DataRequestRepository;
import nl.app.loopsnelheid.privacy.domain.DataRequest;
import nl.app.loopsnelheid.privacy.domain.DataRequestContent;
import nl.app.loopsnelheid.privacy.domain.DataRequestStatus;
import nl.app.loopsnelheid.privacy.domain.event.OnDataRequestCompleteEvent;
import nl.app.loopsnelheid.security.domain.User;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class PrivacyService {
    private final DataRequestRepository dataRequestRepository;
    private final ApplicationEventPublisher eventPublisher;

    private DataRequest createDataRequest(User user)
    {
        return DataRequest.builder()
                .email(user.getEmail())
                .user(user)
                .requestedAt(LocalDateTime.now())
                .dataRequestStatus(DataRequestStatus.CONFIRMED)
                .build();
    }

    public DataRequest saveDataRequest(User authenticatedUser)
    {
        DataRequest dataRequest = createDataRequest(authenticatedUser);

        return dataRequestRepository.save(dataRequest);
    }

    public void markRequestAsFinished(DataRequest dataRequest)
    {
        dataRequest.markAsFinished();
        dataRequestRepository.save(dataRequest);
    }

    public DataRequest getDataRequestById(Long id)
    {
        return dataRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Gegeven data request id bestaat niet"));
    }

    @Transactional
    public void handleRequest(DataRequest dataRequest)
    {
        dataRequest.markAsPending();
        dataRequestRepository.save(dataRequest);

        DataRequestHandler dataRequestHandler = new DataRequestHandler(dataRequest);
        dataRequestHandler.handle();
        DataRequestContent dataRequestContent = dataRequestHandler.getDataRequestContent();

        FileHandler fileHandler = new FileHandler(dataRequestContent);
        fileHandler.handle();
        File file = fileHandler.getFile();

        ArchiveHandler archiveHandler = new ArchiveHandler(file);
        archiveHandler.handle();
        dataRequest.setFilePath(archiveHandler.getPath());

        fileHandler.removeFile();

        eventPublisher.publishEvent(new OnDataRequestCompleteEvent(dataRequest));
    }
}
