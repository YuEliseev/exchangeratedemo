package ru.eliseev.exchangeratedemo.service.gifservice;

import org.springframework.http.ResponseEntity;
import org.thymeleaf.util.StringUtils;

import ru.eliseev.exchangeratedemo.model.DTO.GifDTO;

import java.util.Map;

public class GifFactory {
        public GifDTO getDTO(ResponseEntity<Map<Object, Object>> responseEntity, String tag){
            String response = responseEntity.toString();

            String imageData = StringUtils.substringAfter(response, "original={");
            imageData = StringUtils.substringBefore(imageData, "},");

            String height = StringUtils.substringAfter(imageData, "height=");
            height = StringUtils.substringBefore(height, ", ");

            String width = StringUtils.substringAfter(imageData, "width=");

            String url = StringUtils.substringAfter(imageData, "url=");
            url = StringUtils.substringBefore(url, ",");

            return new GifDTO(url, height, width, tag);
        }
}
