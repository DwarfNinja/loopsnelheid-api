package nl.app.loopsnelheid.measurement.application;

import lombok.RequiredArgsConstructor;
import nl.app.loopsnelheid.measurement.domain.ResearchData;
import nl.app.loopsnelheid.privacy.application.JobService;
import org.jobrunr.jobs.annotations.Job;
import org.jobrunr.scheduling.JobScheduler;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ResearchDataRequestJobService implements JobService<ResearchData>
{
    private final JobScheduler jobScheduler;

    private final ResearchService researchService;
    private ResearchData researchData;

    @Override
    public void initJob(ResearchData request)
    {
        this.researchData = request;
        jobScheduler.schedule(LocalDateTime.now().plusSeconds(10), this::executeJob);
    }

    @Override
    @Job(name = "Executing research data request within a job", retries = 2)
    public void executeJob()
    {
        researchService.handleRequest(researchData);
    }
}
