package kg.kundoluk.school.dto.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class FirebaseTokenRequest {
    private Long userId;
    @NotNull
    private String token;
}
