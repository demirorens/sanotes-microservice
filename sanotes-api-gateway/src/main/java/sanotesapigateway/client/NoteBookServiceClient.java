package sanotesapigateway.client;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import sanotesapigateway.payload.NoteBookResponse;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NoteBookServiceClient {

    private String hostname = "http://notebook-service/";

    private final WebClient.Builder webClientBuilder;

    public Mono<List<NoteBookResponse>> getUserNoteBooks() {
        return webClientBuilder.build()
                .get()
                .uri(hostname + "usernotebooks")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<NoteBookResponse>>() {
                });
    }
}
