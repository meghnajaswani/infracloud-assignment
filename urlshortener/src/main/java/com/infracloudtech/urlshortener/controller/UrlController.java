package com.infracloudtech.urlshortener.controller;

import com.infracloudtech.urlshortener.service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UrlController {

    private final UrlService service;

    @PostMapping("/shorten")
    public ResponseEntity<String> shorten(
            @RequestParam String url,
            HttpServletRequest request) {

        String code = service.shortenUrl(url);

        String baseUrl = request.getRequestURL()
                .toString()
                .replace(request.getRequestURI(), "");

        return ResponseEntity.ok(baseUrl + "/" + code);
    }

    @GetMapping("/{code}")
    public void redirect(@PathVariable String code, HttpServletResponse response) throws IOException {
        String originalUrl = service.getOriginalUrl(code);
        response.sendRedirect(originalUrl);
    }

    @GetMapping("/metrics")
    public ResponseEntity<List<String>> metrics() {
        return ResponseEntity.ok(service.getTop3Domains());
    }
}
