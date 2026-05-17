package com.shop.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

// Вызываем внешний API для получения курса валют
@FeignClient(name = "currency-client", url = "https://open.er-api.com", fallback = CurrencyClientFallback.class)
public interface CurrencyClient {

    // GET https://open.er-api.com/v6/latest/USD
    @GetMapping("/v6/latest/{base}")
    Map<String, Object> getRates(@PathVariable("base") String base);
}
