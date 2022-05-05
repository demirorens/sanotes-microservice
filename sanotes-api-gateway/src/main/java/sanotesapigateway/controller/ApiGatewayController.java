package sanotesapigateway.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.authentication.AbstractOAuth2TokenAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sanotesapigateway.client.NoteBookServiceClient;
import sanotesapigateway.client.NoteServiceClient;
import sanotesapigateway.client.TagServiceClient;
import sanotesapigateway.client.UserServiceClient;
import sanotesapigateway.config.CurrentUser;
import sanotesapigateway.payload.NoteBookResponse;
import sanotesapigateway.payload.TagResponse;
import sanotesapigateway.payload.UserItemsResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/gateway")
public class ApiGatewayController {

    private final NoteServiceClient noteServiceClient;

    private final NoteBookServiceClient noteBookServiceClient;

    private final TagServiceClient tagServiceClient;

    private final UserServiceClient userServiceClient;

    @GetMapping("useritems")
    public Mono<UserItemsResponse> getUserItems(@CurrentUser OAuth2AuthenticatedPrincipal userPrincipal) {

        Mono<UserItemsResponse> userItemsResponse = userServiceClient.getUser();
        Mono<List<TagResponse>> tags = tagServiceClient.getUserTags();
        Mono<List<NoteBookResponse>> noteBooks = noteBookServiceClient.getUserNoteBooks();
        noteBooks = noteBooks.flatMapMany(noteBookResponses -> {
            return Flux.fromIterable(noteBookResponses)
                    .flatMap(noteBookResponse -> {
                        return noteServiceClient.getNoteBookNotes(noteBookResponse.getId().toString())
                                .zipWith(Mono.just(noteBookResponse)).map(tuple -> {
                                    var result = tuple.getT2();
                                    result.setNotes(tuple.getT1());
                                    return result;
                                });
                    });
        }).collectList();

        userItemsResponse = userItemsResponse.zipWith(tags)
                .map(tuple -> {
                    UserItemsResponse userItemsResponse1 = tuple.getT1();
                    List<TagResponse> tags1 = tuple.getT2();
                    userItemsResponse1.setTags(tags1);
                    return userItemsResponse1;
                });
        userItemsResponse = userItemsResponse.zipWith(noteBooks)
                .map(tuple -> {
                    UserItemsResponse userItemsResponse1 = tuple.getT1();
                    List<NoteBookResponse> noteBookResponses1 = tuple.getT2();
                    userItemsResponse1.setNoteBooks(noteBookResponses1);
                    return userItemsResponse1;
                });

        return userItemsResponse;
    }

    @GetMapping("principle")
    public Mono<OAuth2AuthenticatedPrincipal> getPrinciple(@CurrentUser OAuth2AuthenticatedPrincipal userPrincipal) {
        return ReactiveSecurityContextHolder.getContext()
                .map(ctx -> ((OAuth2AuthenticatedPrincipal) ctx.getAuthentication().getPrincipal()));
    }

    @GetMapping("authentication")
    public Mono<String> getAuthentication(@CurrentUser OAuth2AuthenticatedPrincipal userPrincipal) {
        return ReactiveSecurityContextHolder.getContext()
                .map(ctx -> ((AbstractOAuth2TokenAuthenticationToken) ctx.getAuthentication()).getToken().getTokenValue());
    }
}
