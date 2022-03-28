package sanotesnoteservice.payload;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@JsonIdentityInfo(scope = NoteResponse.class,
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class NoteResponse {

    private UUID id;
    private String noteId;
    private String topic;
    private String text;

    private UUID noteBookId;
    private List<UUID> tags;


}
