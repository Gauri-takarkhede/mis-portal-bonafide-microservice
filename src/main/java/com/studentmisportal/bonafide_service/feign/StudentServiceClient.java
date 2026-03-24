package com.studentmisportal.bonafide_service.feign;

import com.studentmisportal.bonafide_service.config.FeignClientConfig;
import com.studentmisportal.bonafide_service.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "backend",
        url = "${mis-service.url}",
        configuration = FeignClientConfig.class   // for JWT forwarding
)
public interface StudentServiceClient {

    @GetMapping("/profile/{mis}")
    UserDto getStudentProfileById(@PathVariable String mis);
}
