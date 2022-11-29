package kg.kundoluk.school.config.config.yandex;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Validated
@Configuration
@ConfigurationProperties(prefix = "yandex.storage.cred")
@Data
public class YandexStorageProperties {
    @NotBlank
    private String accessKey;
    @NotBlank
    private String secretKey;
}
