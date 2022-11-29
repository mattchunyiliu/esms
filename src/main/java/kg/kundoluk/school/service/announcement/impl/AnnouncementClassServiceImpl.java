package kg.kundoluk.school.service.announcement.impl;

import kg.kundoluk.school.dto.announcement.AnnouncementClassRequest;
import kg.kundoluk.school.dto.projection.AnnouncementClassDTO;
import kg.kundoluk.school.model.announcement.AnnouncementClass;
import kg.kundoluk.school.repository.AnnouncementClassRepository;
import kg.kundoluk.school.service.announcement.AnnouncementClassService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AnnouncementClassServiceImpl implements AnnouncementClassService {
    private final AnnouncementClassRepository announcementClassRepository;

    public AnnouncementClassServiceImpl(AnnouncementClassRepository announcementClassRepository) {
        this.announcementClassRepository = announcementClassRepository;
    }

    @Override
    public AnnouncementClass create(AnnouncementClassRequest announcementClassRequest) {
        return announcementClassRepository.save(
                new AnnouncementClass()
                        .setSchoolClass(announcementClassRequest.getSchoolClass())
                        .setAnnouncement(announcementClassRequest.getAnnouncement())
                        .setSchoolCourse(announcementClassRequest.getSchoolCourse())
        );
    }

    @Override
    public void delete(Long id) {
        announcementClassRepository.deleteById(id);
    }

    @Override
    public List<AnnouncementClassDTO> findAllByAnnouncement(Long announcementId) {
        return announcementClassRepository.findAllByAnnouncementId(announcementId);
    }
}
