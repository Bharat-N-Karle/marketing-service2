package com.roomfinder.marketing.controllers;

import com.roomfinder.marketing.dto.request.CarouselRequest;
import com.roomfinder.marketing.dto.response.CarouselResponse;
import com.roomfinder.marketing.dto.response.PostImageResponse;
import com.roomfinder.marketing.model.GenericApiResponse;
import com.roomfinder.marketing.facade.CarouselFacade;
import com.roomfinder.marketing.facade.MediaFacade;
import com.roomfinder.marketing.repositories.entities.PostImage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
        name = "Carousel Controller",
        description = "Handles operations related to carousels in the marketing system."
)
@RequestMapping("/carousel")
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CarouselController {

    MediaFacade mediaFacade;
    CarouselFacade carouselFacade;

    /**
     * Creates a new carousel.
     *
     * @param request carousel creation details
     * @return the created carousel details
     */
    @PostMapping("/created-carousel")
    @PreAuthorize("hasRole('ROLE_ADMIN')") // Requires Bearer token with ADMIN role
    @Operation(
            summary = "Create a new carousel",
            description = "This API allows users to create a new carousel by providing the required information.",
            responses = {
                    @ApiResponse(
                            description = "Carousel created successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CarouselResponse.class))
                    ),
                    @ApiResponse(
                            description = "Invalid request if provided data is incorrect",
                            responseCode = "400"
                    )
            },
            security = {@SecurityRequirement(name = "bearerAuth")}
    )
    public GenericApiResponse<CarouselResponse> created(@RequestBody CarouselRequest request) {
        var result = carouselFacade.created(request);
        return GenericApiResponse.success(result);
    }

    /**
     * Retrieves all carousels.
     *
     * @return list of all carousels
     */
    @GetMapping("/all")
    @Operation(
            summary = "Get all carousels",
            description = "This API returns a list of all carousels currently available in the system.",
            responses = {
                    @ApiResponse(
                            description = "Carousels retrieved successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class))
                    )
            }
    )
    public GenericApiResponse<List<CarouselResponse>> getCarousels() {
        var result = carouselFacade.getCarousels();
        return GenericApiResponse.success(result);
    }

    /**
     * Deletes a carousel by ID.
     *
     * @param id ID of the carousel to delete
     * @return deletion result message
     */
    @DeleteMapping("/delete-carousel/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')") // Requires Bearer token with ADMIN role
    @Operation(
            summary = "Delete a carousel by ID",
            description = "This API allows users to delete a carousel by providing its ID.",
            responses = {
                    @ApiResponse(
                            description = "Carousel deleted successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
                    ),
                    @ApiResponse(
                            description = "Not found if the carousel with the provided ID does not exist",
                            responseCode = "404"
                    )
            },
            security = {@SecurityRequirement(name = "bearerAuth")}
    )
    public GenericApiResponse<String> delete(@PathVariable(value = "id") String id) {
        var result = carouselFacade.deleteCarousel(id);
        return GenericApiResponse.success(result);
    }

    /**
     * Uploads images for a carousel.
     *
     * @param id    ID of the carousel to upload images to
     * @param files list of images to upload
     * @return uploaded image details
     */
    @PostMapping(value = "/upload-images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN')") // Requires Bearer token with ADMIN role
    @Operation(
            summary = "Upload images for a carousel",
            description = "This API allows users to upload one or more images for a carousel by providing the carousel ID and image files.",
            responses = {
                    @ApiResponse(
                            description = "Images uploaded successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Set.class))
                    ),
                    @ApiResponse(
                            description = "Not found if the carousel with the provided ID does not exist",
                            responseCode = "404"
                    )
            },
            security = {@SecurityRequirement(name = "bearerAuth")}
    )
    public GenericApiResponse<Set<PostImageResponse>> uploadPostImagesCarousel(
            @RequestParam String id,
            @RequestPart(value = "files", required = false) List<MultipartFile> files
    ) {
        Set<PostImage> uploadedImages = mediaFacade.uploadImagesCarousel(id, files);

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
