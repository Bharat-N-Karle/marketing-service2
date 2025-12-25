package com.roomfinder.marketing.services;

import com.roomfinder.marketing.dto.request.BannerRequest;
import com.roomfinder.marketing.dto.response.BannerResponse;

import java.util.List;

public interface BannerService {
    BannerResponse createBanner(BannerRequest bannerRequest);
    BannerResponse updateBanner(BannerRequest bannerRequest, String id);
    String deleteBanner(String id);
    List<BannerResponse> getBanners();
}
