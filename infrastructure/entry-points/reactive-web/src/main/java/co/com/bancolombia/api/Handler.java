package co.com.bancolombia.api;

import co.com.bancolombia.model.model.Model;
import co.com.bancolombia.usecase.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class Handler {

    private final UseCase useCase;

    public Mono<ServerResponse> listenPOSTUseCase(ServerRequest serverRequest) {
        String clientIpAddress = serverRequest.headers().header("clientIpAddress").stream().findFirst()
                .orElse("");
        Map<String, Optional<String>> queryParams = new HashMap<>();
        queryParams.put("client_id", serverRequest.queryParam("client_id"));
        queryParams.put("response_type", serverRequest.queryParam("response_type"));
        queryParams.put("scope", serverRequest.queryParam("scope"));
        queryParams.put("request_url", serverRequest.queryParam("request_url"));

        return serverRequest.bodyToMono(Model.class).flatMap(request -> useCase.testGet(queryParams, clientIpAddress)
                .flatMap(responseConsumer -> {
                    System.out.println("Final stage of service: " + request.getDatabase());
                    return ServerResponse.ok().bodyValue(clientIpAddress);
                }));
    }
}
