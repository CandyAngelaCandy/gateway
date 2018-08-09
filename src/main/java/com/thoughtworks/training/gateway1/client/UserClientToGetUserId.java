package com.thoughtworks.training.gateway1.client;

import com.thoughtworks.training.gateway1.dto.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user",serviceId = "user-service")
public interface UserClientToGetUserId {

    @PostMapping("/getUserIdByName")
    int getUserIdByName(@RequestBody String name);

}
