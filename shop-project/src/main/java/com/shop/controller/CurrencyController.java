package com.shop.controller;

import com.shop.feign.CurrencyClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/currency")
public class CurrencyController {

    private final CurrencyClient currencyClient;

    public CurrencyController(CurrencyClient currencyClient) {
        this.currencyClient = currencyClient;
    }

    // GET /api/currency/rates?base=USD — получаем курсы через Feign Client
    // Feign автоматически делает HTTP-запрос к внешнему API
    @GetMapping("/rates")
    public Map<String, Object> getRates(@RequestParam(defaultValue = "USD") String base) {
        return currencyClient.getRates(base);
    }
}
