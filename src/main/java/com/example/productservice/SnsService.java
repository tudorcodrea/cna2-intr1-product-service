package com.example.productservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SnsService {

    private final RestTemplate restTemplate;

    @Value("${dapr.pubsub.name:orders-pubsub}")
    private String pubsubName;

    @Value("${dapr.topic.name:orders-events}")
    private String topicName;

    public SnsService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public void publishProductEvent(Product product) {
        String url = "http://localhost:3500/v1.0/publish/" + pubsubName + "/" + topicName;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Product> entity = new HttpEntity<>(product, headers);
        restTemplate.postForEntity(url, entity, String.class);
    }
}
