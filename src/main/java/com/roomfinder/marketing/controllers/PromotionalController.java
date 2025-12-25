package com.roomfinder.marketing.controllers;

import com.roomfinder.marketing.dto.PageResponse;
import com.roomfinder.marketing.dto.request.PromotionalRequest;
import com.roomfinder.marketing.dto.response.PromotionalResponse;
import com.roomfinder.marketing.model.GenericApiResponse;
import com.roomfinder.marketing.facade.PromotionalFacade;
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
        name = "Promotional Controller",
        description = "API for managing promotional programs."
)
@Slf4j
@RestController
@RequestMapping("/promotional")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PromotionalController {

    PromotionalFacade promotionalFacade;

    /**
     * Get promotional program details by ID.
     *
     * @param id the promotional program ID.
     * @return details of the promotional program.
     */
    @Operation(summary = "Get promotional program by ID")
    @GetMapping("/{id}")
    public GenericApiResponse<PromotionalResponse> getPromotionalById(@PathVariable String id) {
        var result = promotionalFacade.getPromotionalById(id);
        return GenericApiResponse.success(result);
    }

    /**
     * Create a new promotional program.
     *
     * @param request details of the promotional program to create.
     * @return created promotional program details.
     */
    @PostMapping("create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Create promotional program", security = {@SecurityRequirement(name = "bearerAuth")})
    public GenericApiResponse<PromotionalResponse> createPromotional(@RequestBody PromotionalRequest request) {
        var result = promotionalFacade.createPromotional(request);
        return GenericApiResponse.success(result);
    }

    /**
     * Get a paginated list of promotional programs.
     *
     * @param page the page number (default 1).
     * @param size the number of items per page (default 10).
     * @return list of promotional programs.
     */
    @GetMapping("/list-promotional")
    public GenericApiResponse<PageResponse<PromotionalResponse>> getListPromotional(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        var result = promotionalFacade.getListPromotional(page, size);
        return GenericApiResponse.success(result);
    }

    /**
     * Delete a promotional program by ID.
     *
     * @param id ID of the promotional program to delete.
     * @return deletion status message.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Delete promotional program", security = {@SecurityRequirement(name = "bearerAuth")})
    public GenericApiResponse<String> deletePromotional(@PathVariable String id) {
        var result = promotionalFacade.deletePromotional(id);
        return GenericApiResponse.success(result);
    }

    /**
     * Update a promotional program by ID.
     *
     * @param id ID of the promotional program to update.
     * @param request updated promotional program data.
     * @return updated promotional program details.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Update promotional program", security = {@SecurityRequirement(name = "bearerAuth")})
    public GenericApiResponse<PromotionalResponse> updatePromotional(
            @PathVariable String id,
            @RequestBody PromotionalRequest request
    ) {
        var result = promotionalFacade.updatePromotional(id, request);
        return GenericApiResponse.success(result);
    }
}
