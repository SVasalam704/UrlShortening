package com.project1.urlShortnening.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Data
@Document(collection = "UrlTable")
public class Url {
    @Id
    private String id;
    private String originalUrl;
    private String shortUrl;
    private LocalDateTime createDate;
    private LocalDateTime expiresDate;
}
