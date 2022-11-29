package kg.kundoluk.school.config.config.yandex;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Validated
@Configuration
@ConfigurationProperties(prefix = "yandex.storage.dir")
@Data
public class YandexStorageDirProperties {
    @NotBlank
    private String answerAttach;

    @NotBlank
    private String questionAttach;

    @NotBlank
    private String avatar;

    @NotBlank
    private String attachment;
}
