package kg.kundoluk.school.service.assignment.impl;

import kg.kundoluk.school.dto.assignment.AssignmentAttachmentRequest;
import kg.kundoluk.school.model.instructor.AssignmentAttachment;
import kg.kundoluk.school.repository.AssignmentAttachmentRepository;
import kg.kundoluk.school.service.assignment.AssignmentAttachmentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssignmentAttachmentServiceImpl implements AssignmentAttachmentService {
    private final AssignmentAttachmentRepository assignmentAttachmentRepository;

    public AssignmentAttachmentServiceImpl(AssignmentAttachmentRepository assignmentAttachmentRepository) {
        this.assignmentAttachmentRepository = assignmentAttachmentRepository;
    }

    @Override
    public AssignmentAttachment create(AssignmentAttachmentRequest assignmentAttachmentRequest) {
        AssignmentAttachment assignmentAttachment = new AssignmentAttachment();
        assignmentAttachment.setAssignment(assignmentAttachmentRequest.getAssignment());
        assignmentAttachment.setFileUrl(assignmentAttachmentRequest.getFileUrl());
        assignmentAttachment.setOriginalTitle(assignmentAttachmentRequest.getOriginalTitle());
        return assignmentAttachmentRepository.save(assignmentAttachment);
    }

    @Override
    public void delete(Long id) {
        assignmentAttachmentRepository.deleteById(id);
    }

    @Override
    public void deleteList(List<Long> ids) {
        assignmentAttachmentRepository.massDelete(ids);
    }
}
