package sanotesnoteservice.payload;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class ExceptionResponse {
    private String error;
    private Integer status;
    private List<String> messages;
    private Instant timestamp;

    public ExceptionResponse(String error, Integer status, List<String> messages) {
        this.error = error;
        this.status = status;
        setMessages(messages);
        this.timestamp = Instant.now();
    }

    public List<String> getMessages() {
        return messages == null ? null : new ArrayList<>(messages);
    }

    public void setMessages(List<String> messages) {
        if (messages == null)
            this.messages = null;
        else
            this.messages = Collections.unmodifiableList(messages);
    }

}
