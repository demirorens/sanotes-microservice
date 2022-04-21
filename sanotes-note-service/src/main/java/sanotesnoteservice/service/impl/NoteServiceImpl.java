package sanotesnoteservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sanotesnoteservice.exeption.ResourceNotFoundException;
import sanotesnoteservice.model.NoteContainerModel;
import sanotesnoteservice.model.NoteModel;
import sanotesnoteservice.model.NoteVersionModel;
import sanotesnoteservice.payload.ApiResponse;
import sanotesnoteservice.payload.ByIdRequest;
import sanotesnoteservice.repository.NoteContainerRepository;
import sanotesnoteservice.repository.NoteRepository;
import sanotesnoteservice.repository.NoteVersionRepository;
import sanotesnoteservice.service.NoteService;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;

    private final NoteContainerRepository noteContainerRepository;

    private final NoteVersionRepository noteVersionRepository;

    private final ModelMapper modelMapper;

    private static final String USER_DONT_HAVE_PERMISSION = "User don't have permission for this request";


    public NoteContainerModel saveNote(NoteContainerModel noteContainerModel) {
        NoteModel noteModel = NoteModel.builder()
                .topic(noteContainerModel.getTopic())
                .text(noteContainerModel.getText())
                .build();
        //TODO: Add User control for ownership. Is notebook owner and user is same
        noteModel = noteRepository.save(noteModel);
        noteContainerModel.setNoteId(noteModel.getId());
        noteContainerModel = noteContainerRepository.save(noteContainerModel);
        noteContainerModel.setTopic(noteModel.getTopic());
        noteContainerModel.setText(noteModel.getText());
        return noteContainerModel;
    }

    public NoteContainerModel updateNote(NoteContainerModel noteContainerModel) {
        UUID noteId = noteContainerModel.getId();
        NoteContainerModel oldNoteContainerModel = noteContainerRepository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException("Note", "by id", noteId.toString()));
        //TODO: Add User control for ownership. Is notebook owner and user is same
        TypeMap<NoteContainerModel, NoteVersionModel> propertyMapper =
                modelMapper.createTypeMap(NoteContainerModel.class, NoteVersionModel.class);
        propertyMapper.addMapping(NoteContainerModel::getId, NoteVersionModel::setNoteContainerId);
        NoteVersionModel notesVersionModel = modelMapper.map(oldNoteContainerModel, NoteVersionModel.class);
        notesVersionModel.setNoteContainerId(oldNoteContainerModel.getId());
        NoteModel noteModel = NoteModel.builder()
                .topic(noteContainerModel.getTopic())
                .text(noteContainerModel.getText())
                .build();
        noteModel = noteRepository.save(noteModel);
        noteContainerModel.setNoteId(noteModel.getId());
        noteContainerModel = noteContainerRepository.save(noteContainerModel);
        noteContainerModel.setTopic(noteModel.getTopic());
        noteContainerModel.setText(noteModel.getText());
        //  Save old version begin
        noteVersionRepository.save(notesVersionModel);
        //Save old version end
        return noteContainerModel;
    }

    public NoteContainerModel getNote(UUID id) {
        NoteContainerModel noteContainerModel = noteContainerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Note", "by id", id.toString()));
        NoteModel noteModel = noteRepository.findById(noteContainerModel.getNoteId())
                .orElseThrow(() -> new ResourceNotFoundException("Note", "by id", id.toString()));
        //TODO: Add User control for ownership. Is notebook owner and user is same
        noteContainerModel.setNoteId(noteModel.getId());
        noteContainerModel.setTopic(noteModel.getTopic());
        noteContainerModel.setText(noteModel.getText());
        return noteContainerModel;
    }

    public List<NoteVersionModel> getNoteVersions(UUID id) {
        List<NoteVersionModel> noteVersionModels = noteVersionRepository.findByNoteContainerIdEquals(id);
        //TODO: Add User control for ownership. Is notebook owner and user is same
        return noteVersionModels;
    }


    public ApiResponse deleteNote(ByIdRequest byIdRequest) {
        NoteContainerModel noteContainerModel = noteContainerRepository.findById(byIdRequest.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Note", "by id", byIdRequest.getId().toString()));
        //TODO: Add User control for ownership. Is notebook owner and user is same
        noteContainerRepository.delete(noteContainerModel);
        NoteModel noteModel = NoteModel.builder().id(noteContainerModel.getNoteId()).build();
        noteRepository.delete(noteModel);
        return new ApiResponse(Boolean.TRUE, "You successfully delete note ");
    }
}
