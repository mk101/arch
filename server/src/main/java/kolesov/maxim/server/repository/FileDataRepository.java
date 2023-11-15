package kolesov.maxim.server.repository;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class FileDataRepository implements DataRepository<String> {

    @Value("${filename}")
    private String fileName;

    private List<String> memory = new ArrayList<>();

    @PostConstruct
    public void loadFromFile() {
        try {
            memory = Files.readAllLines(Path.of(fileName)).stream()
                    .filter(s -> !s.isBlank())
                    .collect(Collectors.toList());
        } catch (IOException e) {
            log.error("Failed to load data from file", e);
        }
    }

    @Override
    public List<String> findAll() {
        loadFromFile();
        return Collections.unmodifiableList(memory);
    }

    @Override
    public String save(String data) {
        loadFromFile();
        memory.add(data);
        saveToFile();

        return data;
    }

    private void saveToFile() {
        try {
            Files.writeString(Path.of(fileName), String.join("\n", memory),
                    StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
        } catch (IOException e) {
            log.error("Failed to save file", e);
        }
    }

}
