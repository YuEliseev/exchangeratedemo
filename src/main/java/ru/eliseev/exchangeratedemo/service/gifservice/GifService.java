package ru.eliseev.exchangeratedemo.service.gifservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ru.eliseev.exchangeratedemo.clients.GifClient;
import ru.eliseev.exchangeratedemo.config.ApplicationConfig;
import ru.eliseev.exchangeratedemo.model.DTO.GifDTO;

import java.util.Map;

@Service
public class GifService {
    @Autowired
    private ApplicationConfig applicationConfig;
    @Autowired
    private GifClient gifClient;
    public GifDTO getGif(String tag) {

        ResponseEntity<Map<Object, Object>> gifDtoResponse = gifClient.getGif(applicationConfig.getApiKey(), tag);
        GifFactory gf = new GifFactory();

        if (HttpStatus.OK.equals(gifDtoResponse.getStatusCode())) {
            return gf.getDTO(gifDtoResponse, tag);
        }
        throw new IllegalArgumentException("Status code was not 200 OK" + gifDtoResponse.getStatusCodeValue());
    }
}
