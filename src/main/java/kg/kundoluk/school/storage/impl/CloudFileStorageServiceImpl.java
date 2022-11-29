package kg.kundoluk.school.storage.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import kg.kundoluk.school.storage.CloudFileStorageService;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static com.amazonaws.services.s3.model.CannedAccessControlList.Private;
import static com.amazonaws.services.s3.model.CannedAccessControlList.PublicRead;

@Service
public class CloudFileStorageServiceImpl implements CloudFileStorageService {

    private final AmazonS3 cloudStorage;

    public CloudFileStorageServiceImpl(AmazonS3 cloudStorage) {
        this.cloudStorage = cloudStorage;
    }

    @Override
    public String uploadPublicFile(File file, String filename, String bucketName) {
        PutObjectRequest objectRequest = new PutObjectRequest(bucketName, filename, file);
        objectRequest.setCannedAcl(PublicRead);

        cloudStorage.putObject(objectRequest);

        return cloudStorage.getUrl(bucketName, filename).toString();
    }

    @Override
    @SneakyThrows
    public String uploadPublicFile(InputStream inputStream, String filename, String bucketName) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(inputStream.available());

        PutObjectRequest objectRequest = new PutObjectRequest(bucketName, filename, inputStream, metadata);
        objectRequest.setCannedAcl(PublicRead);

        cloudStorage.putObject(objectRequest);

        return cloudStorage.getUrl(bucketName, filename).toString();
    }

    @Override
    public String uploadPrivateFile(File file, String filename, String bucketName) {
        PutObjectRequest objectRequest = new PutObjectRequest(bucketName, filename, file);
        objectRequest.setCannedAcl(Private);

        cloudStorage.putObject(objectRequest);

        return cloudStorage.getUrl(bucketName, filename).toString();
    }

    @Override
    public String uploadPrivateFile(InputStream inputStream, String filename, String bucketName) {
        ObjectMetadata metadata = new ObjectMetadata();
        PutObjectRequest objectRequest = new PutObjectRequest(bucketName, filename, inputStream, metadata);

        objectRequest.setCannedAcl(Private);

        cloudStorage.putObject(objectRequest);

        return cloudStorage.getUrl(bucketName, filename).toString();
    }


    @SneakyThrows
    @Override
    public File downloadFile(String filename, String bucketName) {
        File tempFile = new File("/tmp/app/" + filename);
        GetObjectRequest objectRequest = new GetObjectRequest(bucketName, filename);
        cloudStorage.getObject(objectRequest, tempFile);

        if (tempFile.length() < 1) {
            throw new FileNotFoundException("Not found file with filename = " + filename);
        }

        return tempFile;
    }

    @Override
    public InputStream downloadInputStream(String filename, String bucketName) {
        GetObjectRequest objectRequest = new GetObjectRequest(bucketName, filename);
        S3Object object = cloudStorage.getObject(objectRequest);

        return object.getObjectContent();
    }

    @Override
    public Boolean existFileInStorage(String filename, String bucketName) {
        return cloudStorage.doesObjectExist(bucketName, filename);
    }

    @Override
    public void deleteFile(String filename, String bucketName) {
        cloudStorage.deleteObject(bucketName, filename);
    }
}
