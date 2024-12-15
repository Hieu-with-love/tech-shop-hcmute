package com.hcmute.tech_shop.services.Impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class CurrencyService {

    @Value("${currency.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;

    public CurrencyService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public BigDecimal getExchangeRateVNDToUSD() {
        Map<String, Object> response = restTemplate.getForObject(apiUrl, Map.class);
        Map<String, Object> conversionRates = (Map<String, Object>) response.get("conversion_rates");

        Double vndToUsdRate = (Double) conversionRates.get("USD");
        BigDecimal rate = BigDecimal.valueOf(vndToUsdRate);
        return rate;
    }

    public BigDecimal convertVNDToUSD(BigDecimal amountVND) {
        BigDecimal exchangeRate = getExchangeRateVNDToUSD();
        return amountVND.multiply(exchangeRate).setScale(2, BigDecimal.ROUND_HALF_UP);
    }
}