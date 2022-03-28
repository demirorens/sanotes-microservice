package sanotesnoteservice.payload;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class BooleanResponse {
    private Boolean result;

    public BooleanResponse(Boolean result) {
        this.result = result;
    }

}
