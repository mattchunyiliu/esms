package kg.kundoluk.school.service.storage.impl;

import kg.kundoluk.school.config.config.yandex.YandexStorageDirProperties;
import kg.kundoluk.school.dto.attachment.AttachmentFileResponseDto;
import kg.kundoluk.school.model.enums.UploadFileType;
import kg.kundoluk.school.service.storage.AttachmentFileService;
import kg.kundoluk.school.storage.CloudFileStorageService;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.UUID;

@Service
public class AttachmentFileServiceImpl implements AttachmentFileService {

    private final CloudFileStorageService storageService;
    private final YandexStorageDirProperties dirProperties;

    public AttachmentFileServiceImpl(CloudFileStorageService storageService, YandexStorageDirProperties dirProperties) {
        this.storageService = storageService;
        this.dirProperties = dirProperties;
    }

    @Override
    public AttachmentFileResponseDto uploadMultipartFile(MultipartFile file) throws IOException {
        String dir = dirProperties.getAttachment();
        String fileName = generateFileName(file);
        try (
                InputStream inputStream = new BufferedInputStream(file.getInputStream());
        ) {
            String mimeType = URLConnection.guessContentTypeFromStream(inputStream);
            UploadFileType fileType = UploadFileType.DOCUMENT;
            if (!StringUtils.isEmpty(mimeType) && mimeType.startsWith("image/"))
                fileType = UploadFileType.IMAGE;
            String url = storageService.uploadPublicFile(inputStream, fileName, dir);
            return AttachmentFileResponseDto
                    .builder()
                    .filename(file.getOriginalFilename())
                    .url(url)
                    .fileType(fileType)
                    .build();
        }
    }

    @Override
    public File downloadByKey(String key) {
        return null;
    }

    @Override
    public void delete(String key) {

    }

    private String generateFileName(MultipartFile file) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        return String.format(
                "attachment_%s%s",
                UUID.randomUUID(),
                extension
        );
    }
}
