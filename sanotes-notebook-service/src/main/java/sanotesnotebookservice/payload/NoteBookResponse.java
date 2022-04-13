package sanotesnotebookservice.payload;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@JsonIdentityInfo(scope = NoteBookResponse.class,
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class NoteBookResponse {
    private UUID id;
    private String name;
    private String description;

}
