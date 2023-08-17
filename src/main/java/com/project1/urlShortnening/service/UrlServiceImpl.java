package com.project1.urlShortnening.service;

import com.google.common.hash.Hashing;
import com.project1.urlShortnening.model.Url;
import com.project1.urlShortnening.model.UrlDto;
import com.project1.urlShortnening.repository.UrlRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Service
public class UrlServiceImpl implements UrlService{

    @Autowired
    private UrlRepository urlRepository;

    @Override
    public Url generateshortUrl(UrlDto urlDto) {
        if(StringUtils.isNotBlank(urlDto.getOriginalUrl())){
            String encodeUrl = encodeUrl(urlDto.getOriginalUrl());
            Url url = new Url();
            url.setCreateDate(LocalDateTime.now());
            url.setOriginalUrl(urlDto.getOriginalUrl());
            url.setShortUrl(encodeUrl);
            url.setExpiresDate(getExpirationDate(urlDto.getExpiryDate(),url.getCreateDate()));
            Url urlToPersist = persistshortUrl(url);
            if(urlToPersist != null){
                return urlToPersist;
            }else{
                return null;
            }
        }


        return null;
    }

    private LocalDateTime getExpirationDate(String expiryDate, LocalDateTime createDate) {
        if(StringUtils.isBlank(expiryDate)){
            return createDate.plusSeconds(86400);
        }
        LocalDateTime expirationDate = LocalDateTime.parse(expiryDate);
        return expirationDate;
    }

    private String encodeUrl(String originalUrl) {
        String encodedUrl = "";
        LocalDateTime time = LocalDateTime.now();
        encodedUrl = Hashing.murmur3_32_fixed()
                .hashString(originalUrl.concat(time.toString()), StandardCharsets.UTF_8)
                .toString();
        return  encodedUrl;
    }

    @Override
    public Url persistshortUrl(Url url) {
        Url urlToRet = urlRepository.save(url);
        return urlToRet;
    }

    @Override
    public Url getEncodedUrl(String shortUrl) {
        Url urlToRet = urlRepository.findByshortUrl(shortUrl);
        return urlToRet;
    }

    @Override
    public void deleteshortUrl(Url url) {
        urlRepository.delete(url);
    }
}
