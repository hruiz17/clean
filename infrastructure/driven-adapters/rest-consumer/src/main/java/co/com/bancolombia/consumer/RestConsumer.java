package co.com.bancolombia.consumer;

import co.com.bancolombia.consumer.model.ObjectResponse;
import co.com.bancolombia.model.model.gateways.RestConsumerGateway;
import co.com.bancolombia.model.model.restConsumer.RestConsumerModel;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RestConsumer implements RestConsumerGateway /* implements Gateway from domain */{
    private final WebClient client;


    // these methods are an example that illustrates the implementation of WebClient.
    // You should use the methods that you implement from the Gateway from the domain.
    @CircuitBreaker(name = "testGet" /*, fallbackMethod = "testGetOk"*/)
    public Mono<RestConsumerModel> testGet(Map<String, Optional<String>> queryParams, String clientIpAddress) {
        return client
                .get()
                .uri(uriBuilder -> uriBuilder.queryParam("client_id", queryParams.get("client_id").get())
                        .queryParam("response_type", queryParams.get("response_type").get())
                        .queryParam("scope", queryParams.get("scope").get())
                        .queryParam("request_url", queryParams.get("request_url").get()).build())
                .header("clientIpAddress",clientIpAddress)
                .retrieve()
                .bodyToMono(ObjectResponse.class).flatMap(responseConsumer -> Mono.just(RestConsumerModel.builder()
                        .id(responseConsumer.getData().getUseCase().getId())
                        .urlFrontUseCase(responseConsumer.getData().getUseCase().getUrlFrontUseCase())
                        .build()));
    }

// Possible fallback method
//    public Mono<String> testGetOk(Exception ignored) {
//        return client
//                .get() // TODO: change for another endpoint or destination
//                .retrieve()
//                .bodyToMono(String.class);
//    }

    @CircuitBreaker(name = "testPost")
    public Mono<ObjectResponse> testPost() {
        ObjectRequest request = ObjectRequest.builder()
            .val1("exampleval1")
            .val2("exampleval2")
            .build();
        return client
                .post()
                .body(Mono.just(request), ObjectRequest.class)
                .retrieve()
                .bodyToMono(ObjectResponse.class);
    }
}
