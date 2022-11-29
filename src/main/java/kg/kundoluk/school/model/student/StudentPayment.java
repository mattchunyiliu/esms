package kg.kundoluk.school.model.student;

import kg.kundoluk.school.model.references.ChronicleYear;
import kg.kundoluk.school.model.base.TimedEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "student_payment_history")
public class StudentPayment extends TimedEntity {

    private static final long serialVersionUID = 7970952564225763115L;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chronicle_id")
    private ChronicleYear chronicleYear;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "description")
    private String description;
}
