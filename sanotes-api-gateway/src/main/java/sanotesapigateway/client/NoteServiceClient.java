package sanotesapigateway.client;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import sanotesapigateway.payload.NoteResponse;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class NoteServiceClient {

    private String hostname = "http://note-service/";

    private final WebClient.Builder webClientBuilder;

    public Mono<List<NoteResponse>> getNoteBookNotes(final UUID notebookId) {
        return webClientBuilder.build()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(hostname + "bynotebookid")
                        .queryParam("id", notebookId)
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<NoteResponse>>() {
                });
    }
}
