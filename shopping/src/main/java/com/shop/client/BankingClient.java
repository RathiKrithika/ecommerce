package com.shop.client;

import com.shop.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "banking",
        url = "http://localhost:8082",
          configuration = FeignClientConfig.class)
public interface BankingClient {

    @PostMapping("/bankingproject/pay")
    void transfer(@RequestBody PaymentRequest request);
}
