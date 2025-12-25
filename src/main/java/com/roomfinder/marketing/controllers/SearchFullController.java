package com.roomfinder.marketing.controllers;

import com.roomfinder.marketing.dto.PageResponse;
import com.roomfinder.marketing.dto.response.RoomSalePostResponse;
import com.roomfinder.marketing.model.GenericApiResponse;
import com.roomfinder.marketing.facade.SearchFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "SearchFullController",
        description = "Search for rental or sale room/house posts"
)
@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SearchFullController {

    private final SearchFacade searchFacade;

    /**
     * Search posts by keyword.
     *
     * @param searchTerm the keyword used for searching.
     * @param page       page number (default: 1).
     * @param size       number of results per page (default: 10).
     * @return paginated search results containing room/house posts.
     */
    @Operation(
            summary = "Search posts by keyword",
            description = "Search rental or sale posts using a keyword, with pagination support."
    )
    @GetMapping("/{searchTerm}")
    public GenericApiResponse<PageResponse<RoomSalePostResponse>> searchPosts(
            @Parameter(description = "Keyword used to search room/house posts")
            @PathVariable String searchTerm,

            @Parameter(description = "Page number for search results (default: 1)")
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,

            @Parameter(description = "Number of posts per page (default: 10)")
            @RequestParam(value = "size", required = false, defaultValue = "10") int size
    ) {
        return GenericApiResponse.success(searchFacade.searchPosts(searchTerm, page, size));
    }
}
