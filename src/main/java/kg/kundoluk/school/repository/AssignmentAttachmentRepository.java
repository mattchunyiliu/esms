package kg.kundoluk.school.repository;

import kg.kundoluk.school.model.instructor.AssignmentAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AssignmentAttachmentRepository extends JpaRepository<AssignmentAttachment, Long> {

    @Modifying
    @Query(value = "DELETE FROM assignment_attachments WHERE id IN ?1",nativeQuery = true)
    @Transactional
    void massDelete(List<Long> ids);
}
