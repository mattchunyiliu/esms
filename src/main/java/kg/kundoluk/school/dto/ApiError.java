package kg.kundoluk.school.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiError implements Serializable {
    private String status;
    private String message;
    private HttpStatus error;
    private String timestamp;
    private String path;
    private Integer device;
}
