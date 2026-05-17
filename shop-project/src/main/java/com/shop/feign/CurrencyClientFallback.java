package com.shop.feign;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

// Fallback — если внешний API недоступен, возвращаем значения по умолчанию
@Component
public class CurrencyClientFallback implements CurrencyClient {

    @Override
    public Map<String, Object> getRates(String base) {
        Map<String, Object> fallback = new HashMap<>();
        fallback.put("result", "fallback");
        fallback.put("base_code", base);
        Map<String, Double> rates = new HashMap<>();
        rates.put("KZT", 450.0);  // Дефолтный курс
        rates.put("EUR", 0.92);
        rates.put("RUB", 90.0);
        fallback.put("rates", rates);
        return fallback;
    }
}
