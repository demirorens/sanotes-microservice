package sanotesnotebookservice.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sanotesnotebookservice.model.NoteBookModel;
import sanotesnotebookservice.payload.ApiResponse;
import sanotesnotebookservice.payload.ByIdRequest;
import sanotesnotebookservice.payload.NoteBookRequest;
import sanotesnotebookservice.payload.NoteBookResponse;
import sanotesnotebookservice.service.NoteBookService;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping
public class NoteBookController {

    @Autowired
    private NoteBookService noteBookService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    @PreAuthorize("hasAuthority('sanotes_user')")
    public ResponseEntity<NoteBookResponse> getNoteBook(@RequestParam(value = "id") UUID id) {
        NoteBookModel noteBookModel = noteBookService.getNoteBook(id);
        NoteBookResponse noteBookResponse = modelMapper.map(noteBookModel, NoteBookResponse.class);
        return new ResponseEntity<>(noteBookResponse, HttpStatus.CREATED);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('sanotes_user')")
    public ResponseEntity<NoteBookResponse> addNoteBook(@Valid @RequestBody NoteBookRequest noteBook) {
        NoteBookModel noteBookModel = modelMapper.map(noteBook, NoteBookModel.class);
        noteBookModel = noteBookService.saveNoteBook(noteBookModel);
        NoteBookResponse noteBookResponse = modelMapper.map(noteBookModel, NoteBookResponse.class);
        return new ResponseEntity<>(noteBookResponse, HttpStatus.CREATED);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('sanotes_user')")
    public ResponseEntity<NoteBookResponse> updateNoteBook(@Valid @RequestBody NoteBookRequest noteBook) {
        NoteBookModel noteBookModel = modelMapper.map(noteBook, NoteBookModel.class);
        noteBookModel = noteBookService.updateNoteBook(noteBookModel);
        NoteBookResponse noteBookResponse = modelMapper.map(noteBookModel, NoteBookResponse.class);
        return new ResponseEntity<>(noteBookResponse, HttpStatus.CREATED);
    }


    @DeleteMapping
    @PreAuthorize("hasAuthority('sanotes_user')")
    public ResponseEntity<ApiResponse> deleteNoteBook(@Valid @RequestBody ByIdRequest byIdRequest) {
        ApiResponse apiResponse = noteBookService.deleteNoteBook(byIdRequest);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }


}
