package sanotestagservice.service;


import sanotestagservice.model.TagModel;
import sanotestagservice.payload.ApiResponse;
import sanotestagservice.payload.ByIdRequest;

import java.util.UUID;

public interface TagService {

    TagModel saveTag(TagModel tagModel);

    TagModel updateTag(TagModel tagModel, String userId);

    TagModel getTag(UUID id, String userId);

    ApiResponse deleteTag(ByIdRequest byIdRequest, String userId);
}
