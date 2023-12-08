package kolesov.maxim.viewer.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FeignClient(name = "server", url = "${server.url}")
public interface ServerClient {

    @RequestMapping(method = RequestMethod.GET, value = "/all")
    List<String> getAll();

    @RequestMapping(method = RequestMethod.GET, value = "/roles")
    List<String> getRoles(@RequestHeader("Authorization") String authorization);

    @RequestMapping(method = RequestMethod.POST, value = "/")
    void produceMessage(@RequestBody String message, @RequestHeader("Authorization") String authorization);

    @RequestMapping(method = RequestMethod.POST, value = "/", consumes = "multipart/form-data")
    void produceFile(@RequestPart("file") MultipartFile file, @RequestHeader("Authorization") String authorization);

}
