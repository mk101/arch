package kolesov.maxim.client.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FeignClient(name = "server", url = "${server.url}")
public interface Client {

    @RequestMapping(method = RequestMethod.POST, value = "/")
    void produceMessage(@RequestBody String message, @RequestHeader("Authorization") String authorization);

    @RequestMapping(method = RequestMethod.POST, value = "/", consumes = "multipart/form-data")
    void produceFile(@RequestPart("file") MultipartFile file, @RequestHeader("Authorization") String authorization);

    @RequestMapping(method = RequestMethod.GET, value = "/roles")
    List<String> getRoles(@RequestHeader("Authorization") String authorization);

}
