package kg.kundoluk.school.endpoint.impl;

import kg.kundoluk.school.components.annotations.Endpoint;
import kg.kundoluk.school.dto.announcement.*;
import kg.kundoluk.school.dto.attachment.AttachmentFileResponseDto;
import kg.kundoluk.school.endpoint.AnnouncementEndpoint;
import kg.kundoluk.school.model.announcement.Announcement;
import kg.kundoluk.school.model.school.School;
import kg.kundoluk.school.model.user.User;
import kg.kundoluk.school.repository.*;
import kg.kundoluk.school.service.announcement.AnnouncementAttachmentService;
import kg.kundoluk.school.service.announcement.AnnouncementClassService;
import kg.kundoluk.school.service.announcement.AnnouncementService;
import kg.kundoluk.school.service.storage.AssignmentAttachFileService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Endpoint
public class AnnouncementEndpointImpl implements AnnouncementEndpoint {
    private final AnnouncementService announcementService;
    private final SchoolRepository schoolRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final SchoolCourseRepository schoolCourseRepository;
    private final AssignmentAttachFileService assignmentAttachFileService;
    private final AnnouncementAttachmentService announcementAttachmentService;
    private final AnnouncementClassService announcementClassService;

    public AnnouncementEndpointImpl(AnnouncementService announcementService, SchoolRepository schoolRepository, UserRepository userRepository, RoleRepository roleRepository, SchoolClassRepository schoolClassRepository, SchoolCourseRepository schoolCourseRepository, AssignmentAttachFileService assignmentAttachFileService, AnnouncementAttachmentService announcementAttachmentService, AnnouncementClassService announcementClassService) {
        this.announcementService = announcementService;
        this.schoolRepository = schoolRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.schoolClassRepository = schoolClassRepository;
        this.schoolCourseRepository = schoolCourseRepository;
        this.assignmentAttachFileService = assignmentAttachFileService;
        this.announcementAttachmentService = announcementAttachmentService;
        this.announcementClassService = announcementClassService;
    }

    @Override
    public Announcement create(AnnouncementCreateRequest announcementCreateRequest, MultipartFile[] files) throws IOException {
        User user = userRepository.getOne(announcementCreateRequest.getUserId());
        School school = schoolRepository.getOne(announcementCreateRequest.getSchoolId());

        AnnouncementRequest announcementRequest = AnnouncementRequest.builder()
                .title(announcementCreateRequest.getTitle())
                .description(announcementCreateRequest.getDescription())
                .school(school)
                .roles(roleRepository.findAllByIdIn(announcementCreateRequest.getRoles()))
                .user(user)
                .build();

        Announcement announcement = announcementService.create(announcementRequest);

        if (files != null) {
            saveAttachments(files, announcement);
        }

        if (announcementCreateRequest.getClasses() != null){
            for (AnnouncementClassDto announcementClassDto: announcementCreateRequest.getClasses()){
                AnnouncementClassRequest announcementClassRequest = AnnouncementClassRequest.builder()
                        .announcement(announcement)
                        .schoolClass(schoolClassRepository.getOne(announcementClassDto.getClassId()))
                        .schoolCourse(schoolCourseRepository.getOne(announcementClassDto.getCourseId()))
                        .build();

                announcementClassService.create(announcementClassRequest);
            }
        }

        return announcement;
    }

    @Override
    public List<Announcement> findAllByUser(Long userId) {
        return announcementService.findAllByUser(userId);
    }

    @Override
    public List<Announcement> findAllBySchoolAndRole(Long schoolId, Long roleId, Long classId) {
        if (classId == null)
            classId = 0L;
        return announcementService.findAllBySchoolAndRole(schoolId, roleId, classId);
    }

    @Override
    public void delete(Long id) {
        announcementService.delete(id);
    }

    private void saveAttachments(MultipartFile[] multipartFiles, Announcement announcement) throws IOException {
        for (MultipartFile file: multipartFiles) {
            AttachmentFileResponseDto attachmentFileResponseDto = assignmentAttachFileService.uploadMultipartFile(file);
            AnnouncementAttachmentRequest announcementAttachmentRequest = AnnouncementAttachmentRequest
                    .builder()
                    .announcement(announcement)
                    .fileUrl(attachmentFileResponseDto.getUrl())
                    .originalTitle(attachmentFileResponseDto.getFilename())
                    .build();
            announcementAttachmentService.create(announcementAttachmentRequest);
        }
    }
}
