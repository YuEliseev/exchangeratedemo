package ru.eliseev.exchangeratedemo.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import ru.eliseev.exchangeratedemo.model.RatesDTO;

@Service
@FeignClient(name = "rates-client", url = "${client.ratesUrl}")
public interface RatesClient {

    @GetMapping(value = "latest.json")
    ResponseEntity<RatesDTO> getLatestRates(@RequestParam("app_id") String api_key, @RequestParam("symbols") String symbols);

    @GetMapping(value = "historical/{date}.json")
    ResponseEntity<RatesDTO> getYesterdayRates(@PathVariable("date") String date, @RequestParam("app_id") String api_key, @RequestParam("symbols") String symbols);
}
