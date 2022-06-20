package ru.eliseev.exchangeratedemo.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Service
@FeignClient(name = "gif-client", url = "${client.gifUrl}")
public interface GifClient {

    @GetMapping(value = "/random")
    ResponseEntity<Map<Object, Object>> getGif(@RequestParam("api_key") String api_key, @RequestParam("tag") String tag);
}

