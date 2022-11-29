package kg.kundoluk.school.service.announcement.impl;

import kg.kundoluk.school.dto.announcement.AnnouncementRequest;
import kg.kundoluk.school.model.announcement.Announcement;
import kg.kundoluk.school.repository.AnnouncementRepository;
import kg.kundoluk.school.service.announcement.AnnouncementService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AnnouncementServiceImpl implements AnnouncementService {
    private final AnnouncementRepository announcementRepository;


    public AnnouncementServiceImpl(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;

    }

    @Override
    public Announcement create(AnnouncementRequest announcementRequest) {
        Announcement announcement = new Announcement();
        announcement.setTitle(announcementRequest.getTitle());
        announcement.setDescription(announcementRequest.getDescription());
        announcement.setUser(announcementRequest.getUser());
        announcement.setSchool(announcementRequest.getSchool());
        announcement.setRoles(announcementRequest.getRoles());
        return announcementRepository.save(announcement);
    }

    @Override
    public void delete(Long id) {
        announcementRepository.deleteById(id);
    }

    @Override
    public List<Announcement> findAllByUser(Long userId) {
        return announcementRepository.findAllByUserId(userId);
    }

    @Override
    public List<Announcement> findAllBySchoolAndRole(Long schoolId, Long roleId, Long classId) {
        return announcementRepository.findAllBySchool(schoolId, roleId, classId);
    }
}
