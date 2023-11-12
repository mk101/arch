package kolesov.maxim.viewer.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "server", url = "${server.url}")
public interface ServerClient {

    @RequestMapping(method = RequestMethod.GET, value = "/all")
    List<String> getAll();

}
