package kg.kundoluk.school.dto.student;

import kg.kundoluk.school.model.enums.SubscriptionType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StudentSubscriptionRequest {
    private SubscriptionType subscriptionType;
    private List<Long> studentList;
}
