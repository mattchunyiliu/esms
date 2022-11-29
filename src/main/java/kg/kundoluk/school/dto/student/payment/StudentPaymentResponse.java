package kg.kundoluk.school.dto.student.payment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentPaymentResponse {
    private String createdAt;
    private Double amount;
    private String description;
}
