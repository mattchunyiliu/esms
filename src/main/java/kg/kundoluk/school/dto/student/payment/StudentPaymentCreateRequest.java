package kg.kundoluk.school.dto.student.payment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentPaymentCreateRequest {
    private Long studentId;
    private Long chronicleId;
    private Double amount;
    private String description;
}
