package com.roomfinder.marketing.controllers;

import com.roomfinder.marketing.dto.PageResponse;
import com.roomfinder.marketing.dto.request.BaseIndexRequest;
import com.roomfinder.marketing.dto.response.BaseIndexResponse;
import com.roomfinder.marketing.dto.response.RoomSalePostResponse;
import com.roomfinder.marketing.model.GenericApiResponse;
import com.roomfinder.marketing.facade.BaseIndexFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "Featured Controller",
        description = "API for managing featured (highlighted) posts within the marketing system."
)
@Slf4j
@RestController
@RequestMapping("/featured")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FeaturedController {

    BaseIndexFacade featuredFacade;

    /**
     * Get featured information by ID.
     *
     * @param id the ID of the featured item.
     * @return details of the featured item.
     */
    @Operation(summary = "Get featured item by ID")
    @GetMapping("/{id}")
    public GenericApiResponse<BaseIndexResponse> getFeaturedById(@PathVariable String id) {
        var result = featuredFacade.getFeaturedById(id);
        return GenericApiResponse.success(result);
    }

    /**
     * Create a new featured listing.
     *
     * @param roomId ID of the room to feature.
     * @param typePackage the package type for the featured listing.
     * @return details of the created featured listing.
     */
    @PostMapping("/create")
    @Operation(summary = "Create a featured room", security = {@SecurityRequirement(name = "bearerAuth")})
    public GenericApiResponse<RoomSalePostResponse> createFeatured(
            @RequestParam int typePackage,
            @RequestParam String roomId
    ) {
        var result = featuredFacade.createFeaturedAdsFee(typePackage, roomId);
        return GenericApiResponse.success(result);
    }

    /**
     * Get a paginated list of featured items.
     *
     * @param page page number (default: 1).
     * @param size page size (default: 10).
     * @return paginated list of featured items.
     */
    @GetMapping("/list-featured")
    public GenericApiResponse<PageResponse<BaseIndexResponse>> getFeatures(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size
    ) {
        var result = featuredFacade.getFeatures(page, size);
        return GenericApiResponse.success(result);
    }

    /**
     * Delete a featured item by ID.
     *
     * @param id ID of the featured item to delete.
     * @return deletion result message.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')") // Requires Bearer token with ADMIN role
    @Operation(summary = "Delete a featured item by ID", security = {@SecurityRequirement(name = "bearerAuth")})
    public GenericApiResponse<String> deleteFeatured(@PathVariable(value = "id") String id) {
        var result = featuredFacade.deleteFeatured(id);
        return GenericApiResponse.success(result);
    }

    /**
     * Update a featured item by ID.
     *
     * @param id ID of the featured item to update.
     * @param request new updated featured information.
     * @return updated featured item details.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')") // Requires Bearer token with ADMIN role
    @Operation(summary = "Update a featured item by ID", security = {@SecurityRequirement(name = "bearerAuth")})
    public GenericApiResponse<BaseIndexResponse> updateFeatured(
            @PathVariable(value = "id") String id,
            @RequestBody BaseIndexRequest request
    ) {
        var result = featuredFacade.updateFeatured(id, request);
        return GenericApiResponse.success(result);
    }
}
