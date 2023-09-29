package kolesov.maxim.monitor.client;

import kolesov.maxim.monitor.dto.ServerHealthDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "server", url = "${server.url}")
public interface ServerClient {

    @RequestMapping(method = RequestMethod.GET, value = "/actuator/health", produces = "application/json")
    ServerHealthDto health();

}
