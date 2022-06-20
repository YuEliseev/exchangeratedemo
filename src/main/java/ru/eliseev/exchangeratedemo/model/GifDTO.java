package ru.eliseev.exchangeratedemo.model;

import lombok.Data;

@Data
public class GifDTO {
    private final String url;
    private final String height;
    private final String width;
    private final String tag;
}


