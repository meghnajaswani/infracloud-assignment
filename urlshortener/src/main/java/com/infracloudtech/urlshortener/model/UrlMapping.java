package com.infracloudtech.urlshortener.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UrlMapping {
    private String originalUrl;
    private String shortCode;
}
