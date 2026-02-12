package kbtu.kz.service;


import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class ExternalService {
    private final RestTemplate restTemplate;

    // Constructor Injection
    public ExternalService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Map getDataFromExternalApi() {

        String url = "https://api.chucknorris.io/jokes/random";

        Map response = restTemplate.getForObject(url, Map.class);

        return response;
    }
}
