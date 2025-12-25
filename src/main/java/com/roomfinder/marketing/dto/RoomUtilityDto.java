package com.roomfinder.marketing.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoomUtilityDto {
    String name;
    Map<String, Boolean> furnitureAvailability;
    Map<String, Boolean> amenitiesAvailability;
}
