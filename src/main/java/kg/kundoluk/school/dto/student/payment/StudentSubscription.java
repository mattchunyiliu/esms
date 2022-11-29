package kg.kundoluk.school.dto.student.payment;

import kg.kundoluk.school.model.enums.SubscriptionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class StudentSubscription implements Serializable {
    private List<Long> studentIds;
    private SubscriptionType subscriptionType;
}
