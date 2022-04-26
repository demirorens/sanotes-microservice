package sanotesnoteservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import sanotesnoteservice.payload.BooleanResponse;

import java.util.UUID;

@FeignClient(name = "api-gateway")
public interface NoteBookClient {

    @GetMapping("/api/notebook/owner/{noteBookId}")
    BooleanResponse getIsUserOwner(@PathVariable("noteBookId") String noteBookId);
}
