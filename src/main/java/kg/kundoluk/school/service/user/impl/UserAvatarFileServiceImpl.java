package kg.kundoluk.school.service.user.impl;

import kg.kundoluk.school.config.config.yandex.YandexStorageDirProperties;
import kg.kundoluk.school.dto.attachment.AttachmentFileResponseDto;
import kg.kundoluk.school.service.user.UserAvatarFileService;
import kg.kundoluk.school.storage.CloudFileStorageService;
import kg.kundoluk.school.storage.ImageConvertService;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.UUID;

@Service
public class UserAvatarFileServiceImpl implements UserAvatarFileService {

    private final CloudFileStorageService storageService;
    private final ImageConvertService imageConvertService;
    private final YandexStorageDirProperties dirProperties;

    public UserAvatarFileServiceImpl(CloudFileStorageService storageService, ImageConvertService imageConvertService, YandexStorageDirProperties dirProperties) {
        this.storageService = storageService;
        this.imageConvertService = imageConvertService;
        this.dirProperties = dirProperties;
    }

    @SneakyThrows
    @Override
    public AttachmentFileResponseDto uploadMultipartFile(MultipartFile file) {
        String dir = dirProperties.getAvatar();
        String fileName = generateFileName();
        try (
                InputStream inputStream = new BufferedInputStream(file.getInputStream());
                InputStream jpegInputStream = imageConvertService.convertToJpg(inputStream);
        ) {
            String url = storageService.uploadPublicFile(jpegInputStream, fileName, dir);
            return AttachmentFileResponseDto
                    .builder()
                    .filename(fileName)
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

    private String generateFileName() {
        return String.format(
                "avatar_%s%s",
                UUID.randomUUID(),
                ".jpg"
        );
    }
}
