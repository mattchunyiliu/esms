package kg.kundoluk.school.model.base;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import kg.kundoluk.school.utils.JsonLocalDateTimeSerializer;


import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class TimedEntity extends BaseEntity {

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @JsonSerialize(using = JsonLocalDateTimeSerializer.class)
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @JsonSerialize(using = JsonLocalDateTimeSerializer.class)
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
