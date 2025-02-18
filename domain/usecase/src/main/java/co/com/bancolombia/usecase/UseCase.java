package co.com.bancolombia.usecase;

import co.com.bancolombia.model.model.gateways.RestConsumerGateway;
import co.com.bancolombia.model.model.restConsumer.RestConsumerModel;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class UseCase {
    private final RestConsumerGateway restConsumerGateway;

    public Mono<RestConsumerModel> testGet(Map<String, Optional<String>> queryParams, String clientIpAddress){

        return restConsumerGateway.testGet(queryParams, clientIpAddress).flatMap(restConsumerModel -> {
            System.out.println("Respuesta del consumo del reuso" + restConsumerModel);
            return Mono.just(restConsumerModel);
        });
    }
}
