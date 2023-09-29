package kolesov.maxim.server.controller;

import kolesov.maxim.server.model.DataModel;
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
        List<DataModel> data = getDataService.getAll();
        model.addAttribute("models", data);

        return "data-str";
    }

}
