package com.roomfinder.marketing.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PromotionalRequest {
    String roomId;
    BigDecimal fixPrice;
    Double percent;
}
