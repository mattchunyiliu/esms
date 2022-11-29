package kg.kundoluk.school.dto.attachment;

import kg.kundoluk.school.model.enums.UploadFileType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class AttachmentFileResponseDto {
    String filename;
    String url;
    UploadFileType fileType;
}
