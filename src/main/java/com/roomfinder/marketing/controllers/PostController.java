package com.roomfinder.marketing.controllers;

import com.roomfinder.marketing.dto.PageResponse;
import com.roomfinder.marketing.dto.request.FilterRequest;
import com.roomfinder.marketing.dto.request.RoomSalePostRequest;
import com.roomfinder.marketing.dto.request.SearchPostRequest;
import com.roomfinder.marketing.dto.response.InfoMarketing;
import com.roomfinder.marketing.dto.response.PostImageResponse;
import com.roomfinder.marketing.dto.response.RoomSalePostResponse;
import com.roomfinder.marketing.model.GenericApiResponse;
import com.roomfinder.marketing.facade.MarketingFacade;
import com.roomfinder.marketing.facade.MediaFacade;
import com.roomfinder.marketing.repositories.entities.PostImage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
        name = "Marketing Controller",
        description = "API for managing posts related to rooms for rent or sale."
)
@Slf4j
@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostController {

    MarketingFacade marketingFacade;
    MediaFacade mediaFacade;

    /**
     * Get a room sale or rental post by ID.
     *
     * @param id ID of the post.
     * @return post details.
     */
    @Operation(summary = "Get post by ID", security = {@SecurityRequirement(name = "bearerAuth")})
    @GetMapping("/post-by-id/{id}")
    public GenericApiResponse<RoomSalePostResponse> getRoomSalePostById(@PathVariable String id) {
        var result = marketingFacade.getRoomSalePostById(id);
        return GenericApiResponse.success(result);
    }

    /**
     * Create a new room sale or rental post.
     *
     * @param request post information.
     * @return created post details.
     */
    @PostMapping("/create")
    @Operation(summary = "Create a new post", security = {@SecurityRequirement(name = "bearerAuth")})
    public GenericApiResponse<RoomSalePostResponse> createPost(@RequestBody RoomSalePostRequest request) {
        var result = marketingFacade.createPost(request);
        return GenericApiResponse.success(result);
    }

    /**
     * Get paginated list of room posts.
     */
    @GetMapping("/all")
    public GenericApiResponse<PageResponse<RoomSalePostResponse>> getPosts(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        var result = marketingFacade.getPosts(page, size);
        return GenericApiResponse.success(result);
    }

    /**
     * Get list of featured posts.
     */
    @GetMapping("/list-post-featured")
    public GenericApiResponse<PageResponse<RoomSalePostResponse>> getPostsFeatured(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        var result = marketingFacade.getPostsFeatured(page, size);
        return GenericApiResponse.success(result);
    }

    /**
     * Get list of promotional posts.
     */
    @GetMapping("/list-post-promotional")
    public GenericApiResponse<PageResponse<RoomSalePostResponse>> getPostsPromotional(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        var result = marketingFacade.getPostsPromotional(page, size);
        return GenericApiResponse.success(result);
    }

    /**
     * Delete a post by ID.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Delete a post", security = {@SecurityRequirement(name = "bearerAuth")})
    public GenericApiResponse<String> deletePost(@PathVariable String id) {
        var result = marketingFacade.deletePost(id);
        return GenericApiResponse.success(result);
    }

    /**
     * Update a post by ID.
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update a post", security = {@SecurityRequirement(name = "bearerAuth")})
    public GenericApiResponse<RoomSalePostResponse> updatePost(
            @PathVariable String id,
            @RequestBody RoomSalePostRequest request
    ) {
        var result = marketingFacade.updatePost(id, request);
        return GenericApiResponse.success(result);
    }

    /**
     * Upload images for a post.
     */
    @PostMapping(value = "/upload-images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public GenericApiResponse<Set<PostImageResponse>> uploadPostImages(
            @RequestParam String id,
            @RequestPart(value = "files", required = false) List<MultipartFile> files
    ) {
        Set<PostImage> uploadedImages = mediaFacade.uploadImagesRoomSalePosts(id, files);

        Set<PostImageResponse> responseImages = uploadedImages.stream()
                .map(image -> new PostImageResponse(
                        image.getName(),
                        image.getType(),
                        image.getUrlImagePost()
                ))
                .collect(Collectors.toSet());

        return GenericApiResponse.success(responseImages);
    }

    /**
     * Get promotional post by ID.
     */
    @GetMapping("/post-promotional-by-id/{id}")
    public GenericApiResponse<RoomSalePostResponse> getPostPromotionalById(@PathVariable String id) {
        var result = marketingFacade.getRoomSalePostPromotionalById(id);
        return GenericApiResponse.success(result);
    }

    /**
     * Filter posts based on criteria.
     */
    @GetMapping("/post-filter")
    @Operation(summary = "Get posts by filter")
    public GenericApiResponse<PageResponse<RoomSalePostResponse>> getPostsFilter(
            @ModelAttribute FilterRequest filterRequest,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        var result = marketingFacade.getPostsFilter(filterRequest, page, size);
        return GenericApiResponse.success(result);
    }

    /**
     * Search posts by keyword and filters.
     */
    @PostMapping("/searching")
    public GenericApiResponse<PageResponse<RoomSalePostResponse>> searchRooms(
            @RequestBody SearchPostRequest searchRequest,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        var results = marketingFacade.searchPosts(searchRequest, page, size);
        return GenericApiResponse.success(results);
    }

    /**
     * Get all posts created by the logged-in user.
     */
    @Operation(summary = "Get all posts created by the user", security = {@SecurityRequirement(name = "bearerAuth")})
    @GetMapping("/byUser")
    public GenericApiResponse<PageResponse<RoomSalePostResponse>> getPostByUser(
            @RequestParam String status,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "5") int size
    ) {
        var results = marketingFacade.getPostByUser(status, page, size);
        return GenericApiResponse.success(results);
    }

    /**
     * Get posts by district.
     */
    @GetMapping("/district")
    public GenericApiResponse<PageResponse<RoomSalePostResponse>> getPostByDistricts(
            @RequestParam int district,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "5") int size
    ) {
        return GenericApiResponse.success(marketingFacade.getPostByDistrict(district, page, size));
    }

    /**
     * Get posts by type.
     */
    @GetMapping("/fil-type")
    public GenericApiResponse<PageResponse<RoomSalePostResponse>> getPostByType(
            @RequestParam int type,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "5") int size
    ) {
        return GenericApiResponse.success(marketingFacade.getPostByType(type, page, size));
    }

    /**
     * Get system-wide marketing stats and insights.
     */
    @GetMapping("/info-marketing")
    public GenericApiResponse<InfoMarketing> getInfoMarketing() {
        return GenericApiResponse.success(marketingFacade.getInfoMarketing());
    }
}
