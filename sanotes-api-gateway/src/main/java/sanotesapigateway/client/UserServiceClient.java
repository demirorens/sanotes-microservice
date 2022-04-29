package sanotesapigateway.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import sanotesapigateway.payload.UserResponse;

@Component
@RequiredArgsConstructor
public class UserServiceClient {
    private String hostname = "http://user-service/";

    private final WebClient.Builder webClientBuilder;

    public Mono<UserResponse> getUser() {
        return webClientBuilder.build()
                .get()
                .uri(hostname)
                .retrieve()
                .bodyToMono(UserResponse.class);
    }
}
