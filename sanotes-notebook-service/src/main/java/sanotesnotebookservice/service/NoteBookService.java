package sanotesnotebookservice.service;


import sanotesnotebookservice.model.NoteBookModel;
import sanotesnotebookservice.payload.ApiResponse;
import sanotesnotebookservice.payload.ByIdRequest;

import java.util.UUID;

public interface NoteBookService {

    NoteBookModel saveNoteBook(NoteBookModel noteBookModel);

    NoteBookModel updateNoteBook(NoteBookModel noteBookModel);

    ApiResponse deleteNoteBook(ByIdRequest byIdRequest);

    NoteBookModel getNoteBook(UUID id);
}
