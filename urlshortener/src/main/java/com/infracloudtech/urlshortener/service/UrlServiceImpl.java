package com.infracloudtech.urlshortener.service;

import com.infracloudtech.urlshortener.repository.UrlRepository;
import com.infracloudtech.urlshortener.util.Base62Encoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UrlServiceImpl implements UrlService {

    private final UrlRepository repository;
    private final AtomicLong counter = new AtomicLong(1);

    @Override
    public String shortenUrl(String originalUrl) {

        String existingCode = repository.getCode(originalUrl);
        if (existingCode != null) {
            return existingCode;
        }

        long id = counter.getAndIncrement();
        String shortCode = Base62Encoder.encode(id);

        repository.save(originalUrl, shortCode);

        return shortCode;
    }

    @Override
    public String getOriginalUrl(String shortCode) {
        return repository.getUrl(shortCode);
    }

    @Override
    public List<String> getTop3Domains() {

        Map<String, Long> domainCount =
                repository.getAllUrls().keySet().stream()
                        .collect(Collectors.groupingBy(
                                url -> URI.create(url).getHost(),
                                Collectors.counting()
                        ));

        return domainCount.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue(Comparator.reverseOrder()))
                .limit(3)
                .map(entry -> entry.getKey() + " : " + entry.getValue())
                .collect(Collectors.toList());
    }
}
