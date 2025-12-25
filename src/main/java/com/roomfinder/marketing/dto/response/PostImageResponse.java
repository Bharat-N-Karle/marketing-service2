package com.roomfinder.marketing.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostImageResponse {
    String name;
    String type;
    String urlImagePost;
}
