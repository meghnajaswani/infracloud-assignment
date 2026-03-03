package com.infracloudtech.urlshortener.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ShortenRequest(
        @NotBlank(message = "URL cannot be blank")
        @Pattern(
                regexp = "^(http|https)://.*$",
                message = "URL must start with http:// or https://"
        )
        String url
) {}