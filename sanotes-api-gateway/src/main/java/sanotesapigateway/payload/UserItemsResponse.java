package sanotesapigateway.payload;


import lombok.Data;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.List;

@Data
@NoArgsConstructor
public class UserItemsResponse {
    private String id;
    private String firstname;
    private String lastname;
    private String username;
    private String email;
    private List<NoteBookResponse> noteBooks;
    private List<TagResponse> tags;
}
