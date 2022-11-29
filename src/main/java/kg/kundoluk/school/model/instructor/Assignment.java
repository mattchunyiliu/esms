package kg.kundoluk.school.model.instructor;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import kg.kundoluk.school.model.base.TimedEntity;
import kg.kundoluk.school.model.school.SchoolCourse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "assignment")
public class Assignment extends TimedEntity {

    private static final long serialVersionUID = 5678174183398479583L;
    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private CalendarTopic calendarTopic;

    @Column(name = "deadline_at")
    private LocalDate deadlineAt;

    @OneToMany(mappedBy = "assignment")
    @JsonIgnoreProperties("assignment")
    private List<AssignmentAttachment> attachments;
}
