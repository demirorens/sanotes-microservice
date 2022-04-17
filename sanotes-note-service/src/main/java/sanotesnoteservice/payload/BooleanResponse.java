package sanotesnoteservice.payload;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BooleanResponse {
    private Boolean result;

    public BooleanResponse(Boolean result) {
        this.result = result;
    }

}
