package com.roomfinder.marketing.controllers;

import com.roomfinder.marketing.dto.PageResponse;
import com.roomfinder.marketing.dto.request.NewsRequest;
import com.roomfinder.marketing.dto.response.NewsResponse;
import com.roomfinder.marketing.dto.response.PostImageResponse;
import com.roomfinder.marketing.model.GenericApiResponse;
import com.roomfinder.marketing.facade.MediaFacade;
import com.roomfinder.marketing.facade.NewsFacade;
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
        name = "News Controller",
        description = "API for managing news articles."
)
@Slf4j
@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NewsController {

    NewsFacade newsFacade;
    MediaFacade mediaFacade;

    /**
     * Create a new news article.
     *
     * @param request the details of the news article to create.
     * @return the created news article details.
     */
    @PostMapping("/create-news")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Create a news article", security = {@SecurityRequirement(name = "bearerAuth")})
    public GenericApiResponse<NewsResponse> createNews(@RequestBody NewsRequest request) {
        return GenericApiResponse.success(newsFacade.createNews(request));
    }

    /**
     * Get detailed information about a news article by ID.
     *
     * @param id the ID of the news article.
     * @return the details of the news article.
     */
    @GetMapping("/get-news/{id}")
    @Operation(summary = "Get a news article by ID")
    public GenericApiResponse<NewsResponse> getNewsById(@PathVariable String id) {
        return GenericApiResponse.success(newsFacade.getNewsById(id));
    }

    /**
     * Update a news article by ID.
     *
     * @param id the ID of the news article to update.
     * @param request the updated information.
     * @return the updated news article.
     */
    @PutMapping("/update-news/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Update a news article by ID", security = {@SecurityRequirement(name = "bearerAuth")})
    public GenericApiResponse<NewsResponse> updateNewsById(
            @RequestBody NewsRequest request,
            @PathVariable String id
    ) {
        return GenericApiResponse.success(newsFacade.updateNewsById(request, id));
    }

    /**
     * Delete a news article by ID.
     *
     * @param id the ID of the news article to delete.
     * @return a message confirming the deletion.
     */
    @DeleteMapping("/delete-news/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Delete a news article by ID", security = {@SecurityRequirement(name = "bearerAuth")})
    public GenericApiResponse<String> deleteNewsById(@PathVariable String id) {
        return GenericApiResponse.success(newsFacade.deleteNewsById(id));
    }

    /**
     * Get a paginated list of all news articles.
     *
     * @param page the page number to retrieve (default 1).
     * @param size the number of articles per page (default 10).
     * @return a paginated response containing news articles.
     */
    @GetMapping("/all")
    @Operation(summary = "Get all news articles")
    public GenericApiResponse<PageResponse<NewsResponse>> getAllNews(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size
    ) {
        return GenericApiResponse.success(newsFacade.getAllNews(page, size));
    }

    /**
     * Upload images for a news article.
     *
     * @param id the ID of the news article.
     * @param files list of image files to upload.
     * @return list of uploaded images for the article.
     */
    @PostMapping(value = "/upload-images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Upload images for a news article", security = {@SecurityRequirement(name = "bearerAuth")})
    public GenericApiResponse<Set<PostImageResponse>> uploadPostImagesNews(
            @RequestParam String id,
            @RequestPart(value = "files", required = false) List<MultipartFile> files
    ) {
        Set<PostImage> uploadedImages = mediaFacade.uploadImagesNews(id, files);

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
