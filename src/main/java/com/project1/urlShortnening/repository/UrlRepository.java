package com.project1.urlShortnening.repository;

import com.project1.urlShortnening.model.Url;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends MongoRepository<Url,Long> {
    public Url findByshortUrl(String shortUrl);
}
