package sanotesapigateway.payload;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserResponse {
    private String id;
    private String firstname;
    private String lastname;
    private String username;
    private String email;

}
