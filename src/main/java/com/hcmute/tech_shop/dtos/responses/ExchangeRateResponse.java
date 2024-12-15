package com.hcmute.tech_shop.dtos.responses;

import java.math.BigDecimal;
import java.util.Map;

public class ExchangeRateResponse {
    private String result;
    private Map<String, BigDecimal> rates;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Map<String, BigDecimal> getRates() {
        return rates;
    }

    public void setRates(Map<String, BigDecimal> rates) {
        this.rates = rates;
    }
}