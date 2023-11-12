package kolesov.maxim.server.service;

import kolesov.maxim.server.repository.DataRepository;
import kolesov.maxim.server.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.FileNameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddDataService {

    private final DataRepository<String> dataRepository;
    private final ImageRepository imageRepository;
    private final NotificationService notificationService;

    public void addData(String data) {
        log.info("Save {}", data);
        dataRepository.save(data);

        notificationService.send(data);
    }

    @SneakyThrows
    public String addImage(MultipartFile file) {
        String extension = FileNameUtils.getExtension(file.getOriginalFilename());
        String name = String.format("%s.%s", UUID.randomUUID(), extension);

        imageRepository.upload(name, file.getBytes());

        return name;
    }

}
