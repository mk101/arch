package kolesov.maxim.client.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "server", url = "${server.url}")
public interface Client {

    @RequestMapping(method = RequestMethod.POST, value = "/")
    void produceMessage(@RequestBody String message);

    @RequestMapping(method = RequestMethod.POST, value = "/", consumes = "multipart/form-data")
    void produceFile(@RequestPart("file") MultipartFile file);

}
