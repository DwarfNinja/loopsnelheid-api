package nl.app.loopsnelheid.privacy.application;

import lombok.RequiredArgsConstructor;
import nl.app.loopsnelheid.privacy.data.DataRequestRepository;
import nl.app.loopsnelheid.privacy.domain.DataRequest;
import nl.app.loopsnelheid.privacy.domain.DataRequestStatus;
import nl.app.loopsnelheid.security.domain.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

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
}
