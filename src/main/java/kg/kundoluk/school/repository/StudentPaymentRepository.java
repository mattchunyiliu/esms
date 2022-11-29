package kg.kundoluk.school.repository;

import kg.kundoluk.school.model.student.StudentPayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentPaymentRepository extends JpaRepository<StudentPayment, Long> {
    List<StudentPayment> findAllByStudentIdOrderByCreatedAtDesc(Long id);
}
