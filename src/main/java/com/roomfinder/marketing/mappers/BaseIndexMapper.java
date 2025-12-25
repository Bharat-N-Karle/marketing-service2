package com.roomfinder.marketing.mappers;

import com.roomfinder.marketing.dto.request.BaseIndexRequest;
import com.roomfinder.marketing.dto.response.BaseIndexResponse;
import com.roomfinder.marketing.repositories.entities.FeaturedRoomEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BaseIndexMapper {
    FeaturedRoomEntity toCreateFeaturedRoom(BaseIndexRequest request);

    BaseIndexResponse toFeatureResponse (FeaturedRoomEntity entity);
    @Mapping(target = "id", ignore = true)
    void updateFeaturedRoom(BaseIndexRequest request, @MappingTarget FeaturedRoomEntity entity);

}
