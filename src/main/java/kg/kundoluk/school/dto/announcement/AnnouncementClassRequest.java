package kg.kundoluk.school.dto.announcement;

import kg.kundoluk.school.model.announcement.Announcement;
import kg.kundoluk.school.model.school.SchoolClass;
import kg.kundoluk.school.model.school.SchoolCourse;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AnnouncementClassRequest {
    Announcement announcement;
    SchoolCourse schoolCourse;
    SchoolClass schoolClass;
}
