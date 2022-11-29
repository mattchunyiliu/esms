package kg.kundoluk.school.config.config.yandex;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class YandexStorageConfig {
    private final YandexStorageProperties yandexStorageProperties;

    public YandexStorageConfig(YandexStorageProperties yandexStorageProperties) {
        this.yandexStorageProperties = yandexStorageProperties;
    }

    @Bean
    AmazonS3 initStorage() {
        BasicAWSCredentials credentials = new BasicAWSCredentials(yandexStorageProperties.getAccessKey(), yandexStorageProperties.getSecretKey());

        ClientConfiguration clientConfiguration = new ClientConfiguration();
        clientConfiguration.setMaxErrorRetry(3);
        clientConfiguration.setConnectionTimeout(501000);
        clientConfiguration.setSocketTimeout(501000);

        AwsClientBuilder.EndpointConfiguration endpointConfiguration = new AmazonS3ClientBuilder.EndpointConfiguration(
                "storage.yandexcloud.net", "ru-central1"
        );

        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withClientConfiguration(clientConfiguration)
                .withEndpointConfiguration(endpointConfiguration)
                .withPathStyleAccessEnabled(true)
                .build();
    }
}
