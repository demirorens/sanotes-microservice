package sanotestagservice.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.web.bind.annotation.*;
import sanotestagservice.model.TagModel;
import sanotestagservice.payload.ApiResponse;
import sanotestagservice.payload.ByIdRequest;
import sanotestagservice.payload.TagRequest;
import sanotestagservice.payload.TagResponse;
import sanotestagservice.security.CurrentUser;
import sanotestagservice.service.TagService;

import javax.validation.Valid;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping
public class TagController {

    private final TagService tagService;

    private final ModelMapper modelMapper;

    @PostMapping
    @PreAuthorize("hasAuthority('sanotes_user')")
    public ResponseEntity<TagResponse> addTag(@Valid @RequestBody TagRequest tag) {
        TagModel tagModel = modelMapper.map(tag, TagModel.class);
        tagModel = tagService.saveTag(tagModel);
        TagResponse tagResponse = modelMapper.map(tagModel, TagResponse.class);
        return new ResponseEntity<>(tagResponse, HttpStatus.CREATED);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('sanotes_user')")
    public ResponseEntity<TagResponse> updateTag(@Valid @RequestBody TagRequest tag,
                                                 @CurrentUser OAuth2AuthenticatedPrincipal userPrincipal) {
        TagModel tagModel = modelMapper.map(tag, TagModel.class);
        tagModel = tagService.updateTag(tagModel, userPrincipal.getName());
        TagResponse tagResponse = modelMapper.map(tagModel, TagResponse.class);
        return new ResponseEntity<>(tagResponse, HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('sanotes_user')")
    public ResponseEntity<TagResponse> getTag(@RequestParam(value = "id") UUID id,
                                              @CurrentUser OAuth2AuthenticatedPrincipal userPrincipal) {
        TagModel tagModel = tagService.getTag(id, userPrincipal.getName());
        TagResponse tagResponse = modelMapper.map(tagModel, TagResponse.class);
        return new ResponseEntity<>(tagResponse, HttpStatus.OK);
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('sanotes_user')")
    public ResponseEntity<ApiResponse> deleteTag(@Valid @RequestBody ByIdRequest byIdRequest,
                                                 @CurrentUser OAuth2AuthenticatedPrincipal userPrincipal) {
        ApiResponse apiResponse = tagService.deleteTag(byIdRequest, userPrincipal.getName());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
