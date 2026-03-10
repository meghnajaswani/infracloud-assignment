package com.infracloudtech.urlshortener.service;

import com.infracloudtech.urlshortener.exception.UrlNotFoundException;
import com.infracloudtech.urlshortener.repository.UrlRepository;
import com.infracloudtech.urlshortener.util.Base62Encoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UrlServiceImpl implements UrlService {

    private final UrlRepository repository;
    private final AtomicLong counter = new AtomicLong(1);
    private final Logger log = LoggerFactory.getLogger(UrlServiceImpl.class);



    @Override
    public String shortenUrl(String originalUrl) {


        // Step 1: Check if URL already exists
        String existingCode = repository.getCode(originalUrl);
        if (existingCode != null) {
            return existingCode;
        }

        // Step 2: Generate new ID
        long id = counter.getAndIncrement();
        log.info("Generated ID: {}", id);
        String shortCode = Base62Encoder.encode(id);
        log.info("Generated Short Code: {}", shortCode);

        // Step 3: Save mapping
        repository.save(originalUrl, shortCode);

        return shortCode;
    }

    @Override
    public String getOriginalUrl(String shortCode) {

        String url = repository.getUrl(shortCode);

        if (url == null) {
            throw new UrlNotFoundException("Short URL not found: " + shortCode);
        }

        return url;
    }

    @Override
    public Map<String, Long> getTop3Domains() {

        return repository.getAllUrls().keySet().stream()  // ✅ use keys
                .map(url -> URI.create(url.trim()).getHost())
                .filter(host -> host != null)
                .collect(Collectors.groupingBy(
                        host -> host,
                        Collectors.counting()
                ))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(3)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> a,
                        LinkedHashMap::new
                ));
    }
}
