package kolesov.maxim.server.controller;

import kolesov.maxim.server.service.GetDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class WebController {

    private final GetDataService getDataService;

    @GetMapping
    public String getAll(Model model) {
        List<String> data = getDataService.getAll();
        model.addAttribute("strings", data);

        return "data-str";
    }

}
