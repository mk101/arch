package kolesov.maxim.server.service;

import kolesov.maxim.server.repository.DataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetDataService {

    private final DataRepository<String> dataRepository;

    public List<String> getAll() {
        return dataRepository.findAll();
    }

}
