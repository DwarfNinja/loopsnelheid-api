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

    @OneToOne
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    public DeleteRequest() {}

    public DeleteRequest(User user)
    {
        this.user = user;
    }

    public void setJobId(UUID jobId)
    {
        this.jobId = jobId;
    }
}
