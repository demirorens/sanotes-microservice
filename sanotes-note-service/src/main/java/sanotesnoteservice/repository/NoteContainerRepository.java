package sanotesnoteservice.repository;

import org.springframework.data.repository.CrudRepository;
import sanotesnoteservice.model.NoteContainerModel;

import java.util.List;
import java.util.UUID;


public interface NoteContainerRepository extends CrudRepository<NoteContainerModel, UUID> {

}
