package kg.kundoluk.school.service.async.impl;

import kg.kundoluk.school.dto.assignment.AssignmentAttachmentRequest;
import kg.kundoluk.school.dto.attachment.AttachmentFileResponseDto;
import kg.kundoluk.school.model.instructor.Assignment;
import kg.kundoluk.school.service.assignment.AssignmentAttachmentService;
import kg.kundoluk.school.service.async.AssignmentAttachmentAsyncUploadService;
import kg.kundoluk.school.service.storage.AssignmentAttachFileService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class AssignmentAttachmentAsyncUploadServiceImpl implements AssignmentAttachmentAsyncUploadService {
    private final AssignmentAttachFileService assignmentAttachFileService;
    private final AssignmentAttachmentService assignmentAttachmentService;

    public AssignmentAttachmentAsyncUploadServiceImpl(AssignmentAttachFileService assignmentAttachFileService, AssignmentAttachmentService assignmentAttachmentService) {
        this.assignmentAttachFileService = assignmentAttachFileService;
        this.assignmentAttachmentService = assignmentAttachmentService;
    }

    @Async("asyncExecutor")
    @Override
    public void uploadAttachments(MultipartFile[] multipartFiles, Assignment assignment) throws IOException {
        for (MultipartFile file: multipartFiles) {

            AttachmentFileResponseDto attachmentFileResponseDto = assignmentAttachFileService.uploadMultipartFile(file);
            AssignmentAttachmentRequest assignmentAttachmentRequest = AssignmentAttachmentRequest.builder()
                    .assignment(assignment)
                    .fileUrl(attachmentFileResponseDto.getUrl())
                    .originalTitle(attachmentFileResponseDto.getFilename())
                    .build();
            assignmentAttachmentService.create(assignmentAttachmentRequest);
        }
    }
}
