package nl.app.loopsnelheid.privacy.application;

public interface JobService<T>
{
    void initJob(T request);
    void executeJob();
}
