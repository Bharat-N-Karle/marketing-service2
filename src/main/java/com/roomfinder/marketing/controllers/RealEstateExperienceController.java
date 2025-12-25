package com.roomfinder.marketing.controllers;

import com.roomfinder.marketing.dto.PageResponse;
import com.roomfinder.marketing.dto.request.NewsRequest;
import com.roomfinder.marketing.dto.response.NewsResponse;
import com.roomfinder.marketing.dto.response.PostImageResponse;
import com.roomfinder.marketing.model.GenericApiResponse;
import com.roomfinder.marketing.facade.MediaFacade;
import com.roomfinder.marketing.facade.RealEstateExperienceFacade;
import com.roomfinder.marketing.repositories.entities.PostImage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Tag(
        name = "RealEstateExperience Controller",
        description = "Manage real estate experience posts"
)
@Slf4j
@RestController
@RequestMapping("/experience")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RealEstateExperienceController {

    RealEstateExperienceFacade realEstateExperienceFacade;
    MediaFacade mediaFacade;

    /**
     * Create a new real estate experience post.
     *
     * @param request data required to create the experience post.
     * @return details of the created real estate experience.
     */
    @Operation(
            summary = "Create a real estate experience post",
            description = "Creates a new real estate experience post using the provided data",
            security = {@SecurityRequirement(name = "bearerAuth")}
    )
    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public GenericApiResponse<NewsResponse> createRealEstateExperience(@RequestBody NewsRequest request) {
        var result = realEstateExperienceFacade.createRealEstateExperience(request);
        return GenericApiResponse.success(result);
    }

    /**
     * Get paginated list of all real estate experiences.
     *
     * @param page page number (default: 1).
     * @param size number of items per page (default: 10).
     * @return paginated list of real estate experience posts.
     */
    @Operation(
            summary = "Get all real estate experiences",
            description = "Retrieve all real estate experiences with pagination",
            security = {@SecurityRequirement(name = "bearerAuth")}
    )
    @GetMapping("/all")
    public GenericApiResponse<PageResponse<NewsResponse>> getAllRealEstateExperience(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size
    ) {
        var result = realEstateExperienceFacade.getAllRealEstateExperience(page, size);
        return GenericApiResponse.success(result);
    }

    /**
     * Get detailed information about a real estate experience by ID.
     *
     * @param id ID of the experience post.
     * @return detailed experience information.
     */
    @Operation(
            summary = "Get real estate experience by ID",
            description = "Retrieve detailed information for a real estate experience post by its ID",
            security = {@SecurityRequirement(name = "bearerAuth")}
    )
    @GetMapping("/get/{id}")
    public GenericApiResponse<NewsResponse> getRealEstateExperienceById(@PathVariable String id) {
        var result = realEstateExperienceFacade.getRealEstateExperienceById(id);
        return GenericApiResponse.success(result);
    }

    /**
     * Update a real estate experience post by ID.
     *
     * @param request updated experience data.
     * @param id ID of the experience to update.
     * @return updated experience details.
     */
    @Operation(
            summary = "Update real estate experience",
            description = "Update information for a real estate experience post by ID",
            security = {@SecurityRequirement(name = "bearerAuth")}
    )
    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public GenericApiResponse<NewsResponse> updateRealEstateExperienceById(
            @RequestBody NewsRequest request,
            @PathVariable String id
    ) {
        var result = realEstateExperienceFacade.updateRealEstateExperienceById(request, id);
        return GenericApiResponse.success(result);
    }

    /**
     * Delete a real estate experience post by ID.
     *
     * @param id ID of the experience post to delete.
     * @return deletion result message.
     */
    @Operation(
            summary = "Delete real estate experience",
            description = "Delete a real estate experience post by its ID",
            security = {@SecurityRequirement(name = "bearerAuth")}
    )
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public GenericApiResponse<String> deleteRealEstateExperienceById(@PathVariable String id) {
        var result = realEstateExperienceFacade.deleteRealEstateExperienceById(id);
        return GenericApiResponse.success(result);
    }

    /**
     * Upload images for a real estate experience post.
     *
     * @param id ID of the experience post.
     * @param files list of image files to upload.
     * @return list of uploaded image metadata.
     */
    @Operation(
            summary = "Upload images for real estate experience",
            description = "Upload one or more images for a real estate experience post",
            security = {@SecurityRequirement(name = "bearerAuth")}
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/upload-images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Images uploaded successfully"),
            @ApiResponse(responseCode = "400", description = "Image upload failed")
    })
    public GenericApiResponse<Set<PostImageResponse>> uploadImagesRealEstateEx(
            @RequestParam String id,
            @RequestPart(value = "files", required = false) List<MultipartFile> files
    ) {
        Set<PostImage> uploadedImages = mediaFacade.uploadImagesRealEstateExperience(id, files);

        Set<PostImageResponse> responseImages = uploadedImages.stream()
                .map(image -> new PostImageResponse(
                        image.getName(),
                        image.getType(),
                        image.getUrlImagePost()
                ))
                .collect(Collectors.toSet());

        return GenericApiResponse.success(responseImages);
    }
}
