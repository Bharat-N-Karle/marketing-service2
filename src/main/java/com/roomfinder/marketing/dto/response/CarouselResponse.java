package com.roomfinder.marketing.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CarouselResponse {
    String id;
    String name;
    List<PostImageResponse> postImages;
}