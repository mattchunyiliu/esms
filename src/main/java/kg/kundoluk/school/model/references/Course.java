package kg.kundoluk.school.model.references;

import kg.kundoluk.school.model.base.BaseEntity;
import kg.kundoluk.school.model.enums.CourseType;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "course")
public class Course extends BaseEntity {


    private static final long serialVersionUID = -3773489254938984003L;
    @Column(name = "title")
    private String title;

    @Column(name = "title_kg")
    private String titleKg;

    @Column(name = "title_ru")
    private String titleRu;

    @Column(name = "title_tr")
    private String titleTr;

    @Column(name = "color_hex")
    private String colorHex;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "course_type")
    private CourseType courseType;

    @Override
    public String toString() {
        return "Course{" +
                "title='" + title + '\'' +
                ", titleKg='" + titleKg + '\'' +
                ", titleRu='" + titleRu + '\'' +
                ", colorHex='" + colorHex + '\'' +
                '}';
    }
}
