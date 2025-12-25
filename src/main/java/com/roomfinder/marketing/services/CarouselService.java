package com.roomfinder.marketing.services;

import com.roomfinder.marketing.dto.request.CarouselRequest;
import com.roomfinder.marketing.dto.response.CarouselResponse;

import java.util.List;

public interface CarouselService {
    CarouselResponse createdCarousel(CarouselRequest request);

    List<CarouselResponse> getCarousels();

    void  deleteCarousel(String id);

}