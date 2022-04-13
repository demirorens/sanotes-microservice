package sanotesnotebookservice.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sanotesnotebookservice.exception.ResourceNotFoundException;
import sanotesnotebookservice.model.NoteBookModel;
import sanotesnotebookservice.payload.ApiResponse;
import sanotesnotebookservice.payload.ByIdRequest;
import sanotesnotebookservice.repository.NoteBookRepository;
import sanotesnotebookservice.service.NoteBookService;

import java.util.Optional;
import java.util.UUID;


@Service
public class NoteBookServiceImpl implements NoteBookService {

    @Autowired
    private NoteBookRepository noteBookRepository;

    private static final String USER_DONT_HAVE_PERMISSION = "User don't have permission for this request";

    public NoteBookModel saveNoteBook(NoteBookModel noteBookModel) {
        return noteBookRepository.save(noteBookModel);
    }

    public NoteBookModel updateNoteBook(NoteBookModel noteBookModel) {
        NoteBookModel oldNoteBookModel = noteBookRepository.findById(noteBookModel.getId())
                .orElseThrow(() -> new ResourceNotFoundException("NoteBook", "by id", noteBookModel.getId().toString()));
        oldNoteBookModel.setName(noteBookModel.getName());
        oldNoteBookModel.setDescription(noteBookModel.getDescription());
        return noteBookRepository.save(oldNoteBookModel);
    }

    public ApiResponse deleteNoteBook(ByIdRequest byIdRequest) {
        Optional<NoteBookModel> noteBook = noteBookRepository.findById(byIdRequest.getId());
        if (noteBook.isEmpty()) {
            throw new ResourceNotFoundException("Note", "by id", byIdRequest.getId().toString());
        }
        noteBookRepository.delete(noteBook.get());
        return new ApiResponse(Boolean.TRUE, "You successfully delete notebook ");
    }

    public NoteBookModel getNoteBook(UUID id) {
        NoteBookModel noteBookModel = noteBookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notebook", "by id", id.toString()));
        return noteBookModel;
    }

}
