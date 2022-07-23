package nl.app.loopsnelheid.privacy.application;

import lombok.RequiredArgsConstructor;
import nl.app.loopsnelheid.measurement.application.DefaultMeasureService;
import nl.app.loopsnelheid.measurement.application.StatisticService;
import nl.app.loopsnelheid.measurement.domain.DefaultMeasure;
import nl.app.loopsnelheid.measurement.domain.MeasureStatistic;
import nl.app.loopsnelheid.privacy.application.encoder.PrivacyPdfEncoder;
import nl.app.loopsnelheid.privacy.application.handler.FilePdfHandler;
import nl.app.loopsnelheid.privacy.data.DataRequestRepository;
import nl.app.loopsnelheid.privacy.domain.DataRequest;
import nl.app.loopsnelheid.privacy.domain.DataRequestStatus;
import nl.app.loopsnelheid.privacy.domain.event.OnDataRequestCompleteEvent;
import nl.app.loopsnelheid.privacy.domain.exception.DataRequestNotFoundException;
import nl.app.loopsnelheid.security.domain.User;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PrivacyService {
    private final DataRequestRepository dataRequestRepository;
    private final ApplicationEventPublisher eventPublisher;

    private final StatisticService statisticService;

    private final DefaultMeasureService defaultMeasureService;

    private final FilePdfHandler filePdfHandler;

    public DataRequest saveDataRequest(User authenticatedUser)
    {
        DataRequest dataRequest = new DataRequest(
                authenticatedUser.getEmail(),
                authenticatedUser,
                DataRequestStatus.CONFIRMED,
                LocalDateTime.now()
        );

        return dataRequestRepository.save(dataRequest);
    }

    public void markRequestAsFinished(DataRequest dataRequest)
    {
        dataRequest.markAsFinished();
        dataRequestRepository.save(dataRequest);
    }

    public List<DataRequest> getDataRequests(Long userId)
    {
        return dataRequestRepository.findAllByUserId(userId);
    }

    public DataRequest getDataRequestById(Long id)
    {
        return dataRequestRepository.findById(id)
                .orElseThrow(() -> new DataRequestNotFoundException("Gegeven data request id bestaat niet"));
    }

    @Transactional
    public void handleRequest(DataRequest dataRequest)
    {
        dataRequest.markAsPending();
        dataRequestRepository.save(dataRequest);

        DefaultMeasure defaultMeasure = this.defaultMeasureService.getDefaultMeasureBySexAndAge(
                dataRequest.getSex(),
                dataRequest.getAge()
        );

        List<MeasureStatistic> measureStatistics = List.of(
                statisticService.getAverageMeasuresOfToday(dataRequest.getUser()),
                statisticService.getAverageMeasuresOfCurrentWeek(dataRequest.getUser()),
                statisticService.getAverageMeasuresOfCurrentMonth(dataRequest.getUser()),
                statisticService.getAverageMeasuresOfLatestQuarter(dataRequest.getUser()),
                statisticService.getAverageMeasuresOfLatestHalfYear(dataRequest.getUser()),
                statisticService.getAverageMeasuresOfLatestYear(dataRequest.getUser())
        );

        PrivacyPdfEncoder privacyPdfEncoder = new PrivacyPdfEncoder(dataRequest, measureStatistics, defaultMeasure);
        filePdfHandler.setPrivacyPdfEncoder(privacyPdfEncoder);
        filePdfHandler.handle();
        dataRequest.setFilePath(filePdfHandler.getFile().getPath());

        eventPublisher.publishEvent(new OnDataRequestCompleteEvent(dataRequest));
    }
}
