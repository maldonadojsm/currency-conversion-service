package com.microservices.currencyconversionservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;

@RestController
public class CurrencyConversionController {
    @Autowired
    private Environment environment;

    @GetMapping(path="/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversion(@PathVariable String from, @PathVariable String to,
                                                         @PathVariable BigDecimal quantity){

        HashMap<String, String> uriVariables = new HashMap<>();
        uriVariables.put("from", from);
        uriVariables.put("to", to); //Map values from currency-exchange request to CurrencyConversion class

        //Capture response
        ResponseEntity<CurrencyConversion>  responseEntity =
                new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}",
                        CurrencyConversion.class, uriVariables);

        // Extract response
        CurrencyConversion currencyConversion = responseEntity.getBody();

        String port = environment.getProperty("local.server.port");

        return new CurrencyConversion(currencyConversion.getId(),from, to, quantity,
                currencyConversion.getConversionMultiple(),
                quantity.multiply(currencyConversion.getConversionMultiple()),
                currencyConversion.getEnvironment());
    }
}
