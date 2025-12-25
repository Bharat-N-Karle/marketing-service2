package com.roomfinder.marketing.services;

import com.roomfinder.marketing.dto.request.CarouselRequest;
import com.roomfinder.marketing.dto.response.CategoryResponse;

import java.util.List;

public interface CategoryService {
    CategoryResponse createCategory(CarouselRequest request);

    CategoryResponse updateCategory(String id, CarouselRequest request);

    void deleteCategory(String id);

    CategoryResponse getCategory(String id);

    List<CategoryResponse> getCategories();

    CategoryResponse getCategoryByName(String name);
}
