package com.roomfinder.marketing.services;

import com.roomfinder.marketing.dto.PageResponse;
import com.roomfinder.marketing.dto.request.BaseIndexRequest;
import com.roomfinder.marketing.dto.response.BaseIndexResponse;
import com.roomfinder.marketing.dto.response.RoomSalePostResponse;


public interface BaseIndexService {
    RoomSalePostResponse createFeaturedAdsFee(int typePackage,String roomId);

    BaseIndexResponse updateFeatured(String id, BaseIndexRequest featuredRequest);

    void deleteFeatured(String id);

    BaseIndexResponse getFeaturedById(String id);

    PageResponse<BaseIndexResponse> getFeatures(int page, int size);
}
