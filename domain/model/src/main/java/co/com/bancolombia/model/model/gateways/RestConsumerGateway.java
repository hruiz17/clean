package co.com.bancolombia.model.model.gateways;

import co.com.bancolombia.model.model.restConsumer.RestConsumerModel;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Optional;

public interface RestConsumerGateway {
    public Mono<RestConsumerModel> testGet(Map<String, Optional<String>> queryParams, String clientIpAddress);
}
