package com.microservices.currencyconversionservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;

//Hard coding URL will make scaling services more difficult and stressful -> Use Naming server
//@FeignClient(name="currency-exchange", url="localhost:8000")

/*Eureka Naming Server will now be in charge of automatically assigning
new urls as new microservice instances are executed.
Requirements ->
1. Having added eureka-netflix-client dependency in pom.xml
2. Have a Eureka naming server up and running
 */
@FeignClient(name="currency-exchange")
public interface CurrencyExchangeProxy {

    //Define mapping that will be returning a resource to Currency Conversion microservice
    @GetMapping(path = "/currency-exchange/from/{from}/to/{to}")
    CurrencyConversion retrieveExchangeValue(@PathVariable String from, @PathVariable String to);
}
