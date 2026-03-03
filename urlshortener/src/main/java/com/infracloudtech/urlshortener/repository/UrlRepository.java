package com.infracloudtech.urlshortener.repository;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UrlRepository {

    // originalUrl -> shortCode
    private final Map<String, String> urlToCode = new ConcurrentHashMap<>();

    // shortCode -> originalUrl
    private final Map<String, String> codeToUrl = new ConcurrentHashMap<>();

    public String getCode(String url) {
        return urlToCode.get(url);
    }

    public String getUrl(String code) {
        return codeToUrl.get(code);
    }

    public void save(String url, String code) {
        urlToCode.put(url, code);
        codeToUrl.put(code, url);
    }

    public Map<String, String> getAllUrls() {
        return urlToCode;
    }
}
