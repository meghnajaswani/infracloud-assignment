package com.infracloudtech.urlshortener;

import com.infracloudtech.urlshortener.service.UrlService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UrlServiceTest {

    @Autowired
    private UrlService service;

    @Test
    void shouldReturnSameCodeForSameUrl() {
        String url = "https://youtube.com/test";

        String code1 = service.shortenUrl(url);
        String code2 = service.shortenUrl(url);

        assertEquals(code1, code2);
    }
}
