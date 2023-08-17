package com.project1.urlShortnening.service;

import com.project1.urlShortnening.model.Url;
import com.project1.urlShortnening.model.UrlDto;
import org.springframework.stereotype.Service;

@Service
public interface UrlService {
    public Url generateshortUrl(UrlDto urlDto);

    public Url persistshortUrl(Url url);

    public Url getEncodedUrl(String originalUrl);

    public void deleteshortUrl(Url url);
}
