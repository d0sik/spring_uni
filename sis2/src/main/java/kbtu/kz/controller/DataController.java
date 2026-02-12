package kbtu.kz.controller;
import kbtu.kz.service.ExternalService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class DataController {
    private final ExternalService externalService;

    public DataController(ExternalService externalService) {
        this.externalService = externalService;
    }

    @GetMapping("/data")
    public Map<String, Object> getData() {

        Map externalData = externalService.getDataFromExternalApi();

        Map<String, Object> response = new HashMap<>();
        response.put("source", "external-api");
        response.put("value", externalData.get("value"));

        return response;
    }
}
