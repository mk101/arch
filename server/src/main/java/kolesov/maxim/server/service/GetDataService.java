package kolesov.maxim.server.service;

import kolesov.maxim.server.model.DataModel;
import kolesov.maxim.server.repository.DataRepository;
import kolesov.maxim.server.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetDataService {

    private final DataRepository<String> dataRepository;
    private final ImageRepository imageRepository;

    public List<DataModel> getAll() {
        return dataRepository.findAll().stream()
                .map(DataModel::new).collect(Collectors.toList());
    }

    public ByteArrayResource getImage(String name) {
        byte[] bytes = imageRepository.get(name);
        return new ByteArrayResource(bytes);
    }

}
