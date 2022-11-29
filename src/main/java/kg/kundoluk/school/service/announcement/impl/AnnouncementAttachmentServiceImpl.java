package kg.kundoluk.school.service.announcement.impl;

import kg.kundoluk.school.dto.announcement.AnnouncementAttachmentRequest;
import kg.kundoluk.school.dto.projection.AnnouncementAttachmentDTO;
import kg.kundoluk.school.model.announcement.AnnouncementAttachment;
import kg.kundoluk.school.repository.AnnouncementAttachmentRepository;
import kg.kundoluk.school.service.announcement.AnnouncementAttachmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AnnouncementAttachmentServiceImpl implements AnnouncementAttachmentService {
    private final AnnouncementAttachmentRepository announcementAttachmentRepository;

    public AnnouncementAttachmentServiceImpl(AnnouncementAttachmentRepository announcementAttachmentRepository) {
        this.announcementAttachmentRepository = announcementAttachmentRepository;
    }

    @Override
    public AnnouncementAttachment create(AnnouncementAttachmentRequest announcementAttachmentRequest) {
        return announcementAttachmentRepository.save(
                new AnnouncementAttachment()
                        .setAnnouncement(announcementAttachmentRequest.getAnnouncement())
                        .setFileUrl(announcementAttachmentRequest.getFileUrl())
                        .setOriginalTitle(announcementAttachmentRequest.getOriginalTitle())
        );
    }

    @Override
    public void delete(Long id) {
        announcementAttachmentRepository.deleteById(id);
    }

    @Override
    public void deleteList(List<Long> ids) {

    }

    @Override
    public List<AnnouncementAttachmentDTO> findAllByAnnouncement(Long announcementId) {
        return announcementAttachmentRepository.findAllByAnnouncementId(announcementId);
    }
}
