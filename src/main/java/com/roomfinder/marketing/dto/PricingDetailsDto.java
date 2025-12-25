package com.roomfinder.marketing.dto;

import com.roomfinder.marketing.dto.response.FeeDetailResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PricingDetailsDto {
    String name;
    BigDecimal basePrice;
    BigDecimal electricityCost;
    BigDecimal waterCost;
    List<FeeDetailResponse> additionalFees;
}
