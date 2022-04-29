package sanotesapigateway.client;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import sanotesapigateway.payload.TagResponse;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TagServiceClient {
    private String hostname = "http://tag-service/";

    private final WebClient.Builder webClientBuilder;

    public Mono<List<TagResponse>> getUserTags() {
        return webClientBuilder.build()
                .get()
                .uri(hostname + "usertags")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<TagResponse>>() {
                });
    }
}
