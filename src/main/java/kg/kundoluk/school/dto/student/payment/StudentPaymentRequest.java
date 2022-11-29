package kg.kundoluk.school.dto.student.payment;

import kg.kundoluk.school.model.references.ChronicleYear;
import kg.kundoluk.school.model.student.Student;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StudentPaymentRequest {
    Student student;
    ChronicleYear chronicleYear;
    Double amount;
    String description;
}
