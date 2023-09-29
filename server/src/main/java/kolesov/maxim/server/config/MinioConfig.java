package kolesov.maxim.server.config;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Configuration
public class MinioConfig {

    @Bean
    public MinioClient minioClient(@Value("${minio.url}") String url,
                                   @Value("${minio.access.login}") String login,
                                   @Value("${minio.access.password}") String password,
                                   @Value("${minio.bucket.name}") String bucketName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        MinioClient client = MinioClient.builder()
                .endpoint(url)
                .credentials(login, password)
                .build();

        boolean foundBucket = client.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        if (!foundBucket) {
            client.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }

        return client;
    }

}
