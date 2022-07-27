package nl.app.loopsnelheid.privacy.application;

import lombok.RequiredArgsConstructor;
import nl.app.loopsnelheid.privacy.domain.DataRequest;
import org.jobrunr.jobs.annotations.Job;
import org.jobrunr.scheduling.JobScheduler;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DataRequestJobService implements JobService<DataRequest>
{
    private final JobScheduler jobScheduler;
    private final PrivacyService privacyService;

    private DataRequest dataRequest;

    @Override
    public void initJob(DataRequest dataRequest)
    {
        this.dataRequest = dataRequest;
        jobScheduler.schedule(LocalDateTime.now(), this::executeJob);
    }

    @Override
    @Job(name = "Executing data request within a job", retries = 1)
    public void executeJob()
    {
        privacyService.handleRequest(dataRequest);
    }
}
