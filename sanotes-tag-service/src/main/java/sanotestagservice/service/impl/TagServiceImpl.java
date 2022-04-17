package sanotestagservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sanotestagservice.exception.ResourceNotFoundException;
import sanotestagservice.model.TagModel;
import sanotestagservice.payload.ApiResponse;
import sanotestagservice.payload.ByIdRequest;
import sanotestagservice.repository.TagRepository;
import sanotestagservice.service.TagService;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    private static final String USER_DONT_HAVE_PERMISSION = "User don't have permission for this request";

    public TagModel saveTag(TagModel tagModel) {
        return tagRepository.save(tagModel);
    }

    public TagModel updateTag(TagModel tagModel) {
        TagModel oldTagModel = tagRepository.findById(tagModel.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Tag", "by id", tagModel.getId().toString()));
        oldTagModel.setName(tagModel.getName());
        oldTagModel.setDescription(tagModel.getDescription());
        return tagRepository.save(oldTagModel);
    }

    public TagModel getTag(UUID id) {
        TagModel tag = tagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tag", "by id", id.toString()));
        return tag;
    }

    public ApiResponse deleteTag(ByIdRequest byIdRequest) {
        TagModel tag = tagRepository.findById(byIdRequest.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Tag", "by id", byIdRequest.getId().toString()));
        tagRepository.delete(tag);
        return new ApiResponse(Boolean.TRUE, "You successfully delete tag ");
    }
}
