package teammates.storage.sqlentity;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.hibernate.annotations.UpdateTimestamp;

import teammates.common.util.FieldValidator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Represents a deadline extension entity.
 */
@Entity
@Table(name = "DeadlineExtensions")
public class DeadlineExtension extends BaseEntity {
    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "sessionId", nullable = false)
    private FeedbackSession feedbackSession;

    @Column(nullable = false)
    private Instant endTime;

    @UpdateTimestamp
    @Column(nullable = false)
    private Instant updatedAt;

    protected DeadlineExtension() {
        // required by Hibernate
    }

    public DeadlineExtension(User user, FeedbackSession feedbackSession, Instant endTime) {
        this.setId(UUID.randomUUID());
        this.setUser(user);
        this.setFeedbackSession(feedbackSession);
        this.setEndTime(endTime);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public FeedbackSession getFeedbackSession() {
        return feedbackSession;
    }

    public void setFeedbackSession(FeedbackSession feedbackSession) {
        this.feedbackSession = feedbackSession;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "DeadlineExtension [id=" + id + ", user=" + user + ", feedbackSessionId=" + feedbackSession.getId()
                + ", endTime=" + endTime + ", createdAt=" + getCreatedAt() + ", updatedAt=" + updatedAt + "]";
    }

    @Override
    public int hashCode() {
        return this.getId().hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        } else if (this == other) {
            return true;
        } else if (this.getClass() == other.getClass()) {
            DeadlineExtension otherDe = (DeadlineExtension) other;
            return Objects.equals(this.getId(), otherDe.getId());
        } else {
            return false;
        }
    }

    @Override
    public List<String> getInvalidityInfo() {
        List<String> errors = new ArrayList<>();

        addNonEmptyError(FieldValidator.getInvalidityInfoForTimeForSessionEndAndExtendedDeadlines(
                feedbackSession.getEndTime(), List.of(this)), errors);

        return errors;
    }
}
