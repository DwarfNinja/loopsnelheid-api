package nl.app.loopsnelheid.security.domain;


import lombok.Getter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "delete_requests")
@Getter
public class DeleteRequest
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID jobId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    public DeleteRequest() {}

    public DeleteRequest(Long id, UUID jobId, User user)
    {
        this.id = id;
        this.jobId = jobId;
        this.user = user;
    }

    public DeleteRequest(UUID jobId, User user)
    {
        this.jobId = jobId;
        this.user = user;
    }

    public DeleteRequest(User user)
    {
        this.user = user;
    }

    public void setJobId(UUID jobId)
    {
        this.jobId = jobId;
    }
}
