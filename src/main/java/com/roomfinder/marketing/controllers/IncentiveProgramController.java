package com.roomfinder.marketing.controllers;

import com.roomfinder.marketing.dto.request.IncentiveProgramRequest;
import com.roomfinder.marketing.dto.response.IncentiveProgramResponse;
import com.roomfinder.marketing.dto.response.PostImageResponse;
import com.roomfinder.marketing.model.GenericApiResponse;
import com.roomfinder.marketing.facade.IncentiveProgramFacade;
import com.roomfinder.marketing.facade.MediaFacade;
import com.roomfinder.marketing.repositories.entities.PostImage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Tag(
        name = "Incentive Program Controller",
        description = "API for managing promotional and incentive programs."
)
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/incentive-program")
public class IncentiveProgramController {

    IncentiveProgramFacade incentiveProgramFacade;
    MediaFacade mediaFacade;

    /**
     * Create a new incentive program.
     *
     * @param request the details of the incentive program to create.
     * @return the created incentive program.
     */
    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Create an incentive program", security = {@SecurityRequirement(name = "bearerAuth")})
    public GenericApiResponse<IncentiveProgramResponse> createIncentiveProgram(
            @RequestBody IncentiveProgramRequest request
    ) {
        return GenericApiResponse.success(incentiveProgramFacade.createIncentiveProgram(request));
    }

    /**
     * Update an existing incentive program.
     *
     * @param id the ID of the program to update.
     * @param request the updated details.
     * @return the updated incentive program.
     */
    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Update an incentive program", security = {@SecurityRequirement(name = "bearerAuth")})
    public GenericApiResponse<IncentiveProgramResponse> updateIncentiveProgram(
            @PathVariable String id,
            @RequestBody IncentiveProgramRequest request
    ) {
        return GenericApiResponse.success(incentiveProgramFacade.updateIncentiveProgram(id, request));
    }

    /**
     * Retrieve an incentive program by ID.
     *
     * @param id the program ID.
     * @return the incentive program details.
     */
    @GetMapping("/get/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Get incentive program by ID", security = {@SecurityRequirement(name = "bearerAuth")})
    public GenericApiResponse<IncentiveProgramResponse> getIncentiveProgram(@PathVariable String id) {
        return GenericApiResponse.success(incentiveProgramFacade.getIncentiveProgram(id));
    }

    /**
     * Retrieve incentive program by status.
     *
     * @param status the status ("active", "inactive", etc.)
     * @return the incentive program matching the status.
     */
    @GetMapping("/get-by-status/{status}")
    @Operation(summary = "Get incentive program by status")
    public GenericApiResponse<IncentiveProgramResponse> getIncentiveProgramByStatus(@PathVariable String status) {
        return GenericApiResponse.success(incentiveProgramFacade.getIncentiveProgramByStatus(status));
    }

    /**
     * Delete an incentive program by ID.
     *
     * @param id the program ID.
     * @return deletion result message.
     */
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Delete an incentive program", security = {@SecurityRequirement(name = "bearerAuth")})
    public GenericApiResponse<String> deleteIncentiveProgram(@PathVariable String id) {
        return GenericApiResponse.success(incentiveProgramFacade.deleteIncentiveProgram(id));
    }

    /**
     * Retrieve a list of all incentive programs.
     *
     * @return list of incentive programs.
     */
    @GetMapping("/get-all")
    @Operation(summary = "Get all incentive programs")
    public GenericApiResponse<List<IncentiveProgramResponse>> getIncentivePrograms() {
        return GenericApiResponse.success(incentiveProgramFacade.getAllIncentivePrograms());
    }

    /**
     * Upload images for an incentive program.
     *
     * @param id the program ID.
     * @param files list of image files.
     * @return uploaded images.
     */
    @PostMapping(value = "/upload-images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Upload images for an incentive program", security = {@SecurityRequirement(name = "bearerAuth")})
    public GenericApiResponse<Set<PostImageResponse>> uploadPostImagesCarousel(
            @RequestParam String id,
            @RequestPart(value = "files", required = false) List<MultipartFile> files
    ) {
        Set<PostImage> uploadedImages = mediaFacade.uploadImagesIncentiveProgram(id, files);

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
