package kg.kundoluk.school.model.announcement;

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
@Table(name = "announcement_attachments")
public class AnnouncementAttachment extends BaseEntity {


    private static final long serialVersionUID = -2889656685528534337L;
    @Column(name = "file_url")
    private String fileUrl;

    @Column(name = "original_title")
    private String originalTitle;

    @ManyToOne
    @JoinColumn(name = "announcement_id")
    private Announcement announcement;
}
