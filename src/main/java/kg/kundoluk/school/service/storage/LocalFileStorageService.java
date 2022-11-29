package kg.kundoluk.school.service.storage;

import com.amazonaws.services.kms.model.NotFoundException;
import com.google.cloud.storage.StorageException;
import kg.kundoluk.school.config.properties.StorageProperties;
import kg.kundoluk.school.exception.ResourceNotFoundException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class LocalFileStorageService {

    private final Path fileStorageLocation;

    @Autowired
    public LocalFileStorageService(StorageProperties storageProperties) {

        this.fileStorageLocation = Paths.get(storageProperties.getLocation()).toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new StorageException(1, "Could not create the directory where the uploaded files will be stored.", ex);
        }

    }

    public String storeFile(MultipartFile file, String newFilename) {

        // Normalize file name
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        try {

            // Check if the file's name contains invalid characters
            if (newFilename.contains("..")) {
                throw new StorageException(1, "Sorry! Filename contains invalid path sequence " + fileName);
            }

            if (newFilename.contains(":")) {
                newFilename = newFilename.replace(":", "-");
            }
            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(newFilename);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return newFilename;

        } catch (IOException ex) {
            throw new StorageException(1, "Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new NotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new NotFoundException("File not found " + fileName);
        }
    }

    public Path getAbsolutePathToImage(String filename) {

        return this.fileStorageLocation.resolve(filename).normalize();
    }

    public void deleteFile(String fileName) {

        try {

            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();

            File file = new File(filePath.toUri());

            if (!file.delete())
                throw new ResourceNotFoundException("Could not delete file");

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    public String saveFile(String name, MultipartFile file) {
        if (file == null) {
            return "";
        }
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String newFilename = name + LocalDateTime.now() + "." + extension;

        return storeFile(file, newFilename);
    }
}
