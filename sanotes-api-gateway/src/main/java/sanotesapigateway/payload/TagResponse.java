package sanotesapigateway.payload;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class TagResponse {
    private UUID id;
    private String name;
    private String description;
}
