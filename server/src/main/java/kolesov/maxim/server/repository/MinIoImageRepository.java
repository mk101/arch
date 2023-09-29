package kolesov.maxim.server.repository;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.ByteArrayInputStream;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MinIoImageRepository implements ImageRepository {

    private static final int PART_SIZE = 10485760;

    private final MinioClient minioClient;

    @Value("${minio.bucket.name}")
    private String bucket;

    @Value("${minio.default-folder}")
    private String folder;

    @Override
    @SneakyThrows
    public void upload(String name, byte[] bytes) {
        minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucket)
                    .object(folder + name)
                    .stream(new ByteArrayInputStream(bytes), -1, PART_SIZE)
                .build());
    }

    @Override
    @SneakyThrows
    public byte[] get(String name) {
        return minioClient.getObject(GetObjectArgs.builder()
                    .bucket(bucket)
                    .object(folder + name)
                .build()).readAllBytes();
    }

}
