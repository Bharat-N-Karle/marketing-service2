package com.roomfinder.marketing.repositories;

import com.roomfinder.marketing.model.GenericApiResponse;
import com.roomfinder.marketing.repositories.clients.PaymentClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentRepository {
    PaymentClient paymentClient;
    public String minusBalance(int type,String roomId) {
        String result = null;
        GenericApiResponse<String> clientResponse = paymentClient.minusBalance(type,roomId);

        if (ObjectUtils.isNotEmpty(clientResponse)) {
            result = clientResponse.getData();
        }
        return result;
    }
}
