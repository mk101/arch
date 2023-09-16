package kolesov.maxim.server.service;

import kolesov.maxim.server.repository.DataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddDataService {

    private final DataRepository<String> dataRepository;

    public void addData(String data) {
        log.info("Save {}", data);
        dataRepository.save(data);
    }

}
