package com.infracloudtech.urlshortener.service;

import java.util.List;
import java.util.Map;

public interface UrlService {

    String shortenUrl(String originalUrl);

    String getOriginalUrl(String shortCode);

    Map<String, Long> getTop3Domains();
}