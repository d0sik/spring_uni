package kbtu.kz.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ApiController {

    // GET /api/info
    @GetMapping("/info")
    public Map<String, Object> getInfo() {
        Map<String, Object> response = new HashMap<>();
        response.put("student", "Doszhan Kazi");
        response.put("course", "Spring Framework");
        response.put("week", 1);
        return response;
    }

    // POST /api/calculate
    @PostMapping("/calculate")
    public Map<String, Integer> calculate(@RequestBody Map<String, Integer> request) {

        Integer a = request.get("a");
        Integer b = request.get("b");

        int result = a + b;

        Map<String, Integer> response = new HashMap<>();
        response.put("result", result);

        return response;
    }
}

