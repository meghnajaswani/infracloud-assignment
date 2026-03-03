package com.infracloudtech.urlshortener.controller;

import com.infracloudtech.urlshortener.dto.ShortenRequest;
import com.infracloudtech.urlshortener.dto.ShortenResponse;
import com.infracloudtech.urlshortener.service.UrlService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UrlController {

    private final UrlService service;

    @PostMapping("/shorten")
    public ResponseEntity<ShortenResponse> shorten(
            @Valid @RequestBody ShortenRequest request,
            HttpServletRequest httpRequest) {

        String code = service.shortenUrl(request.url());

        String baseUrl = httpRequest.getRequestURL()
                .toString()
                .replace(httpRequest.getRequestURI(), "");

        return ResponseEntity.ok(
                new ShortenResponse(baseUrl + "/" + code)
        );
    }

    @GetMapping("/{code}")
    public void redirect(@PathVariable String code, HttpServletResponse response) throws IOException {
        String originalUrl = service.getOriginalUrl(code);
        response.sendRedirect(originalUrl);
    }

    @GetMapping("/metrics")
    public ResponseEntity<Map<String, Long>> metrics() {
        return ResponseEntity.ok(service.getTop3Domains());
    }
}
