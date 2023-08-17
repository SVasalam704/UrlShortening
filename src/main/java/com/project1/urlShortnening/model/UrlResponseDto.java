package com.project1.urlShortnening.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UrlResponseDto {
    private String originalUrl;
    private String shortUrl;
    private LocalDateTime expiryDate;
}
