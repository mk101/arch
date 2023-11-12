package kolesov.maxim.server.controller;

import kolesov.maxim.server.model.DataModel;
import kolesov.maxim.server.service.AddDataService;
import kolesov.maxim.server.service.GetDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ServerController {

    private final AddDataService addDataService;
    private final GetDataService getDataService;

    @PreAuthorize("hasRole('TEXT')")
    @PostMapping(consumes = "text/plain")
    public void addData(@RequestBody String data) {
        log.info("-----RECV-----");
        log.info(data);
        log.info("--------------");
        addDataService.addData(data);
    }

    @PostMapping
    @PreAuthorize("hasRole('IMAGE')")
    public void addFile(@RequestParam("file") MultipartFile file) {
        log.info("-----RECV FILE-----");
        log.info(file.getOriginalFilename());
        log.info("-------------------");
        String name = addDataService.addImage(file);
        addDataService.addData(name);
    }

    @GetMapping(value = "/images/{image}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ByteArrayResource getImage(@PathVariable String image) {
        return getDataService.getImage(image);
    }

    @GetMapping(value = "/all")
    public List<String> getAll() {
        return getDataService.getAll().stream()
                .map(DataModel::getData)
                .collect(Collectors.toList());
    }

}
