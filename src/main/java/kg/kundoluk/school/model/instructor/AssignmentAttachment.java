package kg.kundoluk.school.model.instructor;

import kg.kundoluk.school.model.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "assignment_attachments")
public class AssignmentAttachment extends BaseEntity {


    private static final long serialVersionUID = 3307817625578190333L;
    @Column(name = "file_url")
    private String fileUrl;

    @Column(name = "original_title")
    private String originalTitle;

    @ManyToOne
    @JoinColumn(name = "assignment_id")
    private Assignment assignment;
}
