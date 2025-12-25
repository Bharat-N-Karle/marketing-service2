package com.roomfinder.marketing.services;

import com.roomfinder.marketing.dto.PageResponse;
import com.roomfinder.marketing.dto.request.FavoriteRequest;
import com.roomfinder.marketing.dto.response.FavoriteResponse;


public interface FavoriteService {
    FavoriteResponse createFavorite(FavoriteRequest request );
    void deleteFavorite(String id);
    PageResponse<FavoriteResponse> getFavorite(int page, int size);
}
