package sanotesapigateway.payload;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class NoteBookResponse {
    private UUID id;
    private String name;
    private String description;
    private List<NoteResponse> notes;

}
