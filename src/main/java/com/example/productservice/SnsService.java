package com.example.productservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class SnsService {

    private final SnsClient snsClient;
    private final ObjectMapper objectMapper;

    @Value("${sns.topic.arn}")
    private String topicArn;

    public SnsService() {
        this.snsClient = SnsClient.create();
        this.objectMapper = new ObjectMapper();
    }

    public void publishProductEvent(Product product) {
        try {
            String message = objectMapper.writeValueAsString(product);
            PublishRequest request = PublishRequest.builder()
                    .topicArn(topicArn)
                    .message(message)
                    .build();
            PublishResponse response = snsClient.publish(request);
            System.out.println("Published product event: " + response.messageId());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
