package nl.app.loopsnelheid.security.application;

import lombok.RequiredArgsConstructor;
import nl.app.loopsnelheid.privacy.application.JobService;
import nl.app.loopsnelheid.security.domain.DeleteRequest;
import org.jobrunr.jobs.JobId;
import org.jobrunr.jobs.annotations.Job;
import org.jobrunr.scheduling.JobScheduler;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DeleteRequestJobService implements JobService<DeleteRequest>
{
    private final JobScheduler jobScheduler;
    private final DeleteRequestService deleteRequestService;

    private DeleteRequest deleteRequest;

    @Override
    public void initJob(DeleteRequest deleteRequest)
    {
        this.deleteRequest = deleteRequest;
        JobId jobId = jobScheduler.schedule(LocalDateTime.now().plusMinutes(1), this::executeJob);
        deleteRequestService.submitDeleteRequest(jobId.asUUID(), deleteRequest);
    }

    @Override
    @Job(name = "Executing delete request within a job", retries = 1)
    public void executeJob()
    {
        deleteRequestService.handleRequest(deleteRequest);
    }

    public void deleteJob(DeleteRequest deleteRequest)
    {
        jobScheduler.delete(deleteRequest.getJobId());
        deleteRequestService.revokeDeleteRequest(deleteRequest);
    }
}
