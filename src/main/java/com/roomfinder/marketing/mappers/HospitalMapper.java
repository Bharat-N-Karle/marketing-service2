package com.roomfinder.marketing.mappers;

import com.roomfinder.marketing.dto.request.HospitalRoomRequest;
import com.roomfinder.marketing.dto.response.HostpitalRoomResponse;
import com.roomfinder.marketing.repositories.entities.HospitalRoomEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HospitalMapper {

    HospitalRoomEntity toCreate(HospitalRoomRequest request);

    HostpitalRoomResponse toResponse(HospitalRoomEntity entity);
}
