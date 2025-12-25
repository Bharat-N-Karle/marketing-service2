package com.roomfinder.marketing.controllers;

import com.roomfinder.marketing.dto.PageResponse;
import com.roomfinder.marketing.dto.request.FavoriteRequest;
import com.roomfinder.marketing.dto.response.FavoriteResponse;
import com.roomfinder.marketing.model.GenericApiResponse;
import com.roomfinder.marketing.facade.FavoriteFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "Favorite Controller",
        description = "API for managing posts saved as favorites by users in the marketing system."
)
@Slf4j
@RestController
@RequestMapping("/favorite")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FavoriteController {

    FavoriteFacade favoriteFacade;

    /**
     * Save a post to the user's list of favorites.
     */
    @Operation(
            summary = "Save a post to favorites",
            security = {@SecurityRequirement(name = "bearerAuth")}
    )
    @PostMapping("/create")
    public GenericApiResponse<FavoriteResponse> create(@RequestBody FavoriteRequest request) {
        return GenericApiResponse.success(favoriteFacade.create(request));
    }

    /**
     * Remove a post from the user's favorites list.
     */
    @Operation(
            summary = "Remove a post from favorites",
            security = {@SecurityRequirement(name = "bearerAuth")}
    )
    @DeleteMapping("/delete")
    public GenericApiResponse<String> delete(@RequestParam String roomId) {
        return GenericApiResponse.success(favoriteFacade.delete(roomId));
    }

    /**
     * Retrieve all posts the user has saved as favorites.
     */
    @Operation(
            summary = "Retrieve all posts saved by the user",
            security = {@SecurityRequirement(name = "bearerAuth")}
    )
    @GetMapping("/all")
    public GenericApiResponse<PageResponse<FavoriteResponse>> getFavorites(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "5") int size
    ) {
        return GenericApiResponse.success(favoriteFacade.getFavorites(page, size));
    }
}
