package com.project1.urlShortnening.model;

import lombok.Data;

@Data
public class UrlDto {
    private String originalUrl;
    private String expiryDate; //optional
}
