package com.infracloudtech.urlshortener.service;

import java.util.List;

public interface UrlService {

    String shortenUrl(String originalUrl);

    String getOriginalUrl(String shortCode);

    List<String> getTop3Domains();
}