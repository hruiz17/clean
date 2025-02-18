package co.com.bancolombia.model.model.restConsumer;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RestConsumerModel {
    private String id;
    private String urlFrontUseCase;
}
