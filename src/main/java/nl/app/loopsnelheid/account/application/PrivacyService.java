package nl.app.loopsnelheid.account.application;

import lombok.RequiredArgsConstructor;
import nl.app.loopsnelheid.account.data.DataRequestRepository;
import nl.app.loopsnelheid.account.domain.DataRequest;
import nl.app.loopsnelheid.account.domain.DataRequestStatus;
import nl.app.loopsnelheid.account.domain.User;
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
