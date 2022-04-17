package sanotesnoteservice.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sanotesnoteservice.model.NoteContainerModel;
import sanotesnoteservice.model.NoteVersionModel;
import sanotesnoteservice.payload.ApiResponse;
import sanotesnoteservice.payload.ByIdRequest;
import sanotesnoteservice.payload.NoteRequest;
import sanotesnoteservice.payload.NoteResponse;
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
    public ResponseEntity<NoteResponse> addNote(@Valid @RequestBody NoteRequest noteRequest) {
        NoteContainerModel noteContainerModel = modelMapper.map(noteRequest, NoteContainerModel.class);
        noteContainerModel = noteService.saveNote(noteContainerModel);
        NoteResponse noteResponse = modelMapper.map(noteContainerModel, NoteResponse.class);
        return new ResponseEntity<>(noteResponse, HttpStatus.CREATED);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('sanotes_user')")
    public ResponseEntity<NoteResponse> updateNote(@Valid @RequestBody NoteRequest noteRequest) {
        NoteContainerModel noteContainerModel = modelMapper.map(noteRequest, NoteContainerModel.class);
        noteContainerModel = noteService.updateNote(noteContainerModel);
        NoteResponse noteResponse = modelMapper.map(noteContainerModel, NoteResponse.class);
        return new ResponseEntity<>(noteResponse, HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('sanotes_user')")
    public ResponseEntity<NoteResponse> getNote(@RequestParam(value = "id") UUID id) {
        NoteContainerModel noteContainerModel = noteService.getNote(id);
        NoteResponse noteResponse = modelMapper.map(noteContainerModel, NoteResponse.class);
        return new ResponseEntity<>(noteResponse, HttpStatus.OK);
    }

    @GetMapping("/versions")
    @PreAuthorize("hasAuthority('sanotes_user')")
    public ResponseEntity<List<NoteResponse>> getNoteVersions(@RequestParam(value = "id") UUID id) {
        List<NoteVersionModel> noteVersionModels = noteService.getNoteVersions(id);
        List<NoteResponse> noteResponses = Arrays.asList(modelMapper.map(noteVersionModels, NoteResponse[].class));
        return new ResponseEntity<>(noteResponses, HttpStatus.OK);
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('sanotes_user')")
    public ResponseEntity<ApiResponse> deleteNote(@Valid @RequestBody ByIdRequest byIdRequest) {
        ApiResponse apiResponse = noteService.deleteNote(byIdRequest);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }


}
