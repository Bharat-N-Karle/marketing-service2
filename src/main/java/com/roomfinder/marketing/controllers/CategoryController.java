package com.roomfinder.marketing.controllers;

import com.roomfinder.marketing.dto.request.CarouselRequest;
import com.roomfinder.marketing.dto.response.CategoryResponse;
import com.roomfinder.marketing.dto.response.PostImageResponse;
import com.roomfinder.marketing.model.GenericApiResponse;
import com.roomfinder.marketing.facade.CategoryFacade;
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
        name = "Category Controller",
        description = "API for managing categories within the system."
)
@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryController {

    CategoryFacade categoryFacade;
    MediaFacade mediaFacade;

    /**
     * Creates a new category.
     *
     * @param request the category creation details.
     * @return details of the newly created category.
     */
    @PostMapping("/created-category")
    @PreAuthorize("hasRole('ROLE_ADMIN')") // Requires Bearer token with ADMIN role
    @Operation(summary = "Create a new category", security = {@SecurityRequirement(name = "bearerAuth")})
    public GenericApiResponse<CategoryResponse> created(@RequestBody CarouselRequest request) {
        return GenericApiResponse.success(categoryFacade.createdCategory(request));
    }

    /**
     * Updates an existing category by ID.
     *
     * @param request category update details.
     * @param id the ID of the category to update.
     * @return details of the updated category.
     */
    @PutMapping("/update-category/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')") // Requires Bearer token with ADMIN role
    @Operation(summary = "Update a category by ID", security = {@SecurityRequirement(name = "bearerAuth")})
    public GenericApiResponse<CategoryResponse> update(@RequestBody CarouselRequest request, @PathVariable(value = "id") String id) {
        return GenericApiResponse.success(categoryFacade.updateCategory(id, request));
    }

    /**
     * Deletes a category by ID.
     *
     * @param id the ID of the category to delete.
     * @return the result message after deletion.
     */
    @DeleteMapping("/delete-category/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')") // Requires Bearer token with ADMIN role
    @Operation(summary = "Delete a category by ID", security = {@SecurityRequirement(name = "bearerAuth")})
    public GenericApiResponse<String> delete(@PathVariable(value = "id") String id) {
        return GenericApiResponse.success(categoryFacade.deleteCategory(id));
    }

    /**
     * Retrieves category details by ID.
     *
     * @param id the ID of the category.
     * @return details of the category.
     */
    @GetMapping("/get-category/{id}")
    @Operation(summary = "Get a category by ID")
    public GenericApiResponse<CategoryResponse> getCategory(@PathVariable(value = "id") String id) {
        return GenericApiResponse.success(categoryFacade.getCategory(id));
    }

    /**
     * Retrieves all categories.
     *
     * @return list of all categories in the system.
     */
    @GetMapping("/get-categories")
    @Operation(summary = "Get all categories")
    public GenericApiResponse<List<CategoryResponse>> getCategories() {
        return GenericApiResponse.success(categoryFacade.getCategories());
    }

    /**
     * Retrieves a category by name.
     *
     * @param name the name of the category.
     * @return details of the category with the given name.
     */
    @GetMapping("/get-category-by-name/{name}")
    @Operation(summary = "Get a category by name")
    public GenericApiResponse<CategoryResponse> getCategoryByName(@PathVariable(value = "name") String name) {
        return GenericApiResponse.success(categoryFacade.getCategoryByName(name));
    }

    /**
     * Uploads images for a category by ID.
     *
     * @param id the ID of the category.
     * @param files list of image files to upload.
     * @return list of uploaded image details.
     */
    @PostMapping(value = "/upload-images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN')") // Requires Bearer token with ADMIN role
    @Operation(summary = "Upload images for a category by ID", security = {@SecurityRequirement(name = "bearerAuth")})
    public GenericApiResponse<Set<PostImageResponse>> uploadPostImagesCarousel(
            @RequestParam String id,
            @RequestPart(value = "files", required = false) List<MultipartFile> files
    ) {
        Set<PostImage> uploadedImages = mediaFacade.uploadImagesCategory(id, files);

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
