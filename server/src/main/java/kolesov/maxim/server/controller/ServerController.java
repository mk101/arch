package kolesov.maxim.server.controller;

import kolesov.maxim.server.service.AddDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ServerController {

    private final AddDataService addDataService;

    @PostMapping
    public void addData(@RequestBody String data) {
        log.info("-----RECV-----");
        log.info(data);
        log.info("--------------");
        addDataService.addData(data);
    }

}
