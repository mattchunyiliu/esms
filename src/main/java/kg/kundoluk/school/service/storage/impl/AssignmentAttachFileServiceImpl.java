package kg.kundoluk.school.service.storage.impl;

import kg.kundoluk.school.config.config.yandex.YandexStorageDirProperties;
import kg.kundoluk.school.dto.attachment.AttachmentFileResponseDto;
import kg.kundoluk.school.service.storage.AssignmentAttachFileService;
import kg.kundoluk.school.storage.CloudFileStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FilenameUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class AssignmentAttachFileServiceImpl implements AssignmentAttachFileService {

    private final CloudFileStorageService storageService;
    private final YandexStorageDirProperties dirProperties;

    public AssignmentAttachFileServiceImpl(CloudFileStorageService storageService, YandexStorageDirProperties dirProperties) {
        this.storageService = storageService;
        this.dirProperties = dirProperties;
    }

    @Override
    public AttachmentFileResponseDto uploadMultipartFile(MultipartFile file) throws IOException {
        String dir = dirProperties.getQuestionAttach();
        String fileName = generateFileName(file);
        try (
                InputStream inputStream = new BufferedInputStream(file.getInputStream());
        ) {
            String url = storageService.uploadPublicFile(inputStream, fileName, dir);
            return AttachmentFileResponseDto
                    .builder()
                    .filename(file.getOriginalFilename())
                    .url(url)
                    .build();
        }
    }

    @Override
    public File downloadByKey(String key) {
        String dir = dirProperties.getAvatar();

        return storageService.downloadFile(key, dir);
    }

    @Override
    public void delete(String key) {
        String dir = dirProperties.getAvatar();
        storageService.deleteFile(key, dir);
    }

    private String generateFileName(MultipartFile file) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        return String.format(
                "assignment_%s.%s",
                UUID.randomUUID(),
                extension
        );
    }
}
