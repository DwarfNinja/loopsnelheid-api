package nl.app.loopsnelheid.privacy.application;

import lombok.RequiredArgsConstructor;
import nl.app.loopsnelheid.meassurement.domain.Measure;
import nl.app.loopsnelheid.privacy.data.DataRequestRepository;
import nl.app.loopsnelheid.privacy.domain.DataRequest;
import nl.app.loopsnelheid.privacy.domain.DataRequestContent;
import nl.app.loopsnelheid.privacy.domain.DataRequestStatus;
import nl.app.loopsnelheid.privacy.domain.PersonalData;
import nl.app.loopsnelheid.privacy.presentation.dto.DataRequestDto;
import nl.app.loopsnelheid.security.domain.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PrivacyService {
    private final DataRequestRepository dataRequestRepository;

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

    public DataRequest getRequestById(Long id)
    {
        return dataRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Gegeven data request id bestaat niet"));
    }
    
    public void handleRequest(DataRequest dataRequest)
    {
        dataRequest.markAsPending();
        dataRequestRepository.save(dataRequest);

        DataRequestHandler dataRequestHandler = new DataRequestHandler(dataRequest);
        DataRequestContent dataRequestContent = dataRequestHandler.getDataRequestContent();

        // Stop deze datarequest content in een zip of rar bestand
        // verstuur hem naar de gebruiker
    }
}
