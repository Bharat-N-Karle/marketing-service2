package com.roomfinder.marketing.controllers;

import com.roomfinder.marketing.dto.request.BannerRequest;
import com.roomfinder.marketing.dto.response.BannerResponse;
import com.roomfinder.marketing.model.GenericApiResponse;
import com.roomfinder.marketing.facade.BannerFacade;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@Tag(
        name = "Banner Controller",
        description = "Controller for handling operations related to banners in the marketing system."
)
@Slf4j
@RestController
@RequestMapping("/banner")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BannerController {

    BannerFacade bannerFacade;
    @GetMapping("/test")
    public String hello(){
        return "Hello Bharat";
    }
    /**
     * Creates a new banner.
     *
     * @param request banner creation information
     * @return details of the created banner
     */
    @PostMapping("/created-banner")
    @PreAuthorize("hasRole('ROLE_ADMIN')") // Requires Bearer token with ADMIN role
    @Operation(
            summary = "Create a new banner",
            description = "This API allows users to create a new banner by providing the required information.",
            responses = {
                    @ApiResponse(
                            description = "Banner created successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BannerResponse.class))
                    ),
                    @ApiResponse(
                            description = "Invalid request if the provided data is incorrect",
                            responseCode = "400"
                    )
            },
            security = {@SecurityRequirement(name = "bearerAuth")}
    )
    public GenericApiResponse<BannerResponse> created(@RequestBody BannerRequest request) {
        return GenericApiResponse.success(bannerFacade.createBanner(request));
    }

    /**
     * Deletes a banner by ID.
     *
     * @param id ID of the banner to delete
     * @return result message of the deletion
     */
    @DeleteMapping("/delete-banner/{id}")
/*
    @PreAuthorize("hasRole('ROLE_ADMIN')") // Requires Bearer token with ADMIN role
*/
    @Operation(
            summary = "Delete a banner by ID",
            description = "This API allows users to delete a banner by providing its ID.",
            responses = {
                    @ApiResponse(
                            description = "Banner deleted successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
                    ),
                    @ApiResponse(
                            description = "Not found if the banner with the provided ID does not exist",
                            responseCode = "404"
                    )
            },
            security = {@SecurityRequirement(name = "bearerAuth")}
    )
    public GenericApiResponse<String> delete(@PathVariable(value = "id") String id) {
        return GenericApiResponse.success(bannerFacade.deleteBanner(id));
    }

    /**
     * Retrieves all banners.
     *
     * @return list of banners
     */
    @GetMapping("/all")
    @Operation(
            summary = "Get all banners",
            description = "This API returns a list of all banners available in the system.",
            responses = {
                    @ApiResponse(
                            description = "List of banners retrieved successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class))
                    )
            }
    )
    public GenericApiResponse<List<BannerResponse>> getBanners() {
        return GenericApiResponse.success(bannerFacade.getBanners());
    }

    /**
     * Updates an existing banner by ID.
     *
     * @param request banner update information
     * @param id ID of the banner to update
     * @return updated banner details
     */
    @PutMapping("/update-banner/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')") // Requires Bearer token with ADMIN role
    @Operation(
            summary = "Update a banner",
            description = "This API allows users to update an existing banner by providing its ID and new information.",
            responses = {
                    @ApiResponse(
                            description = "Banner updated successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BannerResponse.class))
                    ),
                    @ApiResponse(
                            description = "Not found if the banner with the provided ID does not exist",
                            responseCode = "404"
                    )
            },
            security = {@SecurityRequirement(name = "bearerAuth")}
    )
    public GenericApiResponse<BannerResponse> update(@RequestBody BannerRequest request, @PathVariable(value = "id") String id) {
        return GenericApiResponse.success(bannerFacade.updateBanner(request, id));
    }
}
