package kg.kundoluk.school.storage;

import java.io.File;
import java.io.InputStream;

public interface CloudFileStorageService {
    String uploadPublicFile(File file, String filename, String bucketName);

    String uploadPublicFile(InputStream inputStream, String filename, String bucketName);

    String uploadPrivateFile(File file,  String filename, String bucketName);

    String uploadPrivateFile(InputStream inputStream,  String filename, String bucketName);

    File downloadFile(String filename, String bucketName);

    InputStream downloadInputStream(String filename, String bucketName);

    Boolean existFileInStorage(String filename, String bucketName);

    void deleteFile(String filename, String bucketName);
}
