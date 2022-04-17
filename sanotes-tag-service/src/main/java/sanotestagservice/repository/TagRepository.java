package sanotestagservice.repository;

import org.springframework.data.repository.CrudRepository;
import sanotestagservice.model.TagModel;

import java.util.Optional;
import java.util.UUID;

public interface TagRepository extends CrudRepository<TagModel, UUID> {
    Optional<TagModel> findByName(String name);


}
