package sanotesnoteservice.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.web.bind.annotation.*;
import sanotesnoteservice.model.NoteContainerModel;
import sanotesnoteservice.model.NoteVersionModel;
import sanotesnoteservice.payload.ApiResponse;
import sanotesnoteservice.payload.ByIdRequest;
import sanotesnoteservice.payload.NoteRequest;
import sanotesnoteservice.payload.NoteResponse;
import sanotesnoteservice.security.CurrentUser;
import sanotesnoteservice.service.NoteService;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping
public class NoteController {

    private final NoteService noteService;

    private final ModelMapper modelMapper;

    @PostMapping
    @PreAuthorize("hasAuthority('sanotes_user')")
    public ResponseEntity<NoteResponse> addNote(@Valid @RequestBody NoteRequest noteRequest,
                                                @CurrentUser OAuth2AuthenticatedPrincipal userPrincipal) {
        NoteContainerModel noteContainerModel = modelMapper.map(noteRequest, NoteContainerModel.class);
        noteContainerModel = noteService.saveNote(noteContainerModel, userPrincipal.getName());
        NoteResponse noteResponse = modelMapper.map(noteContainerModel, NoteResponse.class);
        return new ResponseEntity<>(noteResponse, HttpStatus.CREATED);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('sanotes_user')")
    public ResponseEntity<NoteResponse> updateNote(@Valid @RequestBody NoteRequest noteRequest,
                                                   @CurrentUser OAuth2AuthenticatedPrincipal userPrincipal) {
        NoteContainerModel noteContainerModel = modelMapper.map(noteRequest, NoteContainerModel.class);
        noteContainerModel = noteService.updateNote(noteContainerModel, userPrincipal.getName());
        NoteResponse noteResponse = modelMapper.map(noteContainerModel, NoteResponse.class);
        return new ResponseEntity<>(noteResponse, HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('sanotes_user')")
    public ResponseEntity<NoteResponse> getNote(@RequestParam(value = "id") UUID id,
                                                @CurrentUser OAuth2AuthenticatedPrincipal userPrincipal) {
        NoteContainerModel noteContainerModel = noteService.getNote(id, userPrincipal.getName());
        NoteResponse noteResponse = modelMapper.map(noteContainerModel, NoteResponse.class);
        return new ResponseEntity<>(noteResponse, HttpStatus.OK);
    }

    @GetMapping("/versions")
    @PreAuthorize("hasAuthority('sanotes_user')")
    public ResponseEntity<List<NoteResponse>> getNoteVersions(@RequestParam(value = "id") UUID id,
                                                              @CurrentUser OAuth2AuthenticatedPrincipal userPrincipal) {
        List<NoteVersionModel> noteVersionModels = noteService.getNoteVersions(id, userPrincipal.getName());
        List<NoteResponse> noteResponses = Arrays.asList(modelMapper.map(noteVersionModels, NoteResponse[].class));
        return new ResponseEntity<>(noteResponses, HttpStatus.OK);
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('sanotes_user')")
    public ResponseEntity<ApiResponse> deleteNote(@Valid @RequestBody ByIdRequest byIdRequest,
                                                  @CurrentUser OAuth2AuthenticatedPrincipal userPrincipal) {
        ApiResponse apiResponse = noteService.deleteNote(byIdRequest, userPrincipal.getName());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }


}
