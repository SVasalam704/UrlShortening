package com.project1.urlShortnening.controller;

import com.project1.urlShortnening.model.Url;
import com.project1.urlShortnening.model.UrlDto;
import com.project1.urlShortnening.model.UrlErrorResponseDto;
import com.project1.urlShortnening.model.UrlResponseDto;
import com.project1.urlShortnening.service.UrlService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;

@RestController
public class UrlShorteningController extends HttpServlet {

    @Autowired
    private UrlService urlService;

    @PostMapping("/create/shortUrl")
    public ResponseEntity<?> createshortUrl(@RequestBody UrlDto urlDto){
        Url urlToRet = urlService.generateshortUrl(urlDto);
        if(urlToRet!=null){
            UrlResponseDto urlResponseDto = new UrlResponseDto();
            urlResponseDto.setShortUrl(urlToRet.getShortUrl());
            urlResponseDto.setOriginalUrl(urlToRet.getOriginalUrl());
            urlResponseDto.setExpiryDate(urlToRet.getExpiresDate());
            return new ResponseEntity<UrlResponseDto>(urlResponseDto, HttpStatus.OK);
        }
            UrlErrorResponseDto urlErrorResponseDto = new UrlErrorResponseDto();
            urlErrorResponseDto.setHttpStatus("404");
            urlErrorResponseDto.setStatusMsg("Url not found");
            return new ResponseEntity<UrlErrorResponseDto>(urlErrorResponseDto, HttpStatus.OK);
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<?> redirectToOriginalUrl(@PathVariable String shortUrl, HttpServletResponse response) throws IOException {
        if(StringUtils.isEmpty(shortUrl)){
            UrlErrorResponseDto urlErrorResponseDto = new UrlErrorResponseDto();
            urlErrorResponseDto.setHttpStatus("404");
            urlErrorResponseDto.setStatusMsg("shortLink is empty or inValid");
            return new ResponseEntity<UrlErrorResponseDto>(urlErrorResponseDto, HttpStatus.OK);
        }

        Url urlToRet = urlService.getEncodedUrl(shortUrl);

        if(urlToRet == null){
            UrlErrorResponseDto urlErrorResponseDto = new UrlErrorResponseDto();
            urlErrorResponseDto.setHttpStatus("404");
            urlErrorResponseDto.setStatusMsg("Url is invalid or reached expiration time");
            return new ResponseEntity<UrlErrorResponseDto>(urlErrorResponseDto, HttpStatus.OK);
        }

        if(urlToRet.getExpiresDate().isBefore(LocalDateTime.now())){
            urlService.deleteshortUrl(urlToRet);
            UrlErrorResponseDto urlErrorResponseDto = new UrlErrorResponseDto();
            urlErrorResponseDto.setHttpStatus("200");
            urlErrorResponseDto.setStatusMsg("Url expired please try generating a new url");
            return new ResponseEntity<UrlErrorResponseDto>(urlErrorResponseDto, HttpStatus.OK);
        }
        response.sendRedirect(urlToRet.getOriginalUrl());
        return null;

    }

}
