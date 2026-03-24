package com.studentmisportal.bonafide_service.config;

import feign.RequestInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class FeignClientConfig {

//    @Bean
//    public RequestInterceptor jwtForwardingInterceptor() {
//        return requestTemplate -> {
//            ServletRequestAttributes attrs =
//                    (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//
//            if (attrs != null) {
//                HttpServletRequest request = attrs.getRequest();
//                String authHeader = request.getHeader("Authorization");
//                if (authHeader != null && authHeader.startsWith("Bearer ")) {
//                    requestTemplate.header("Authorization", authHeader);
//                }
//            }
//        };
//    }
    @Bean
    public RequestInterceptor jwtForwardingInterceptor() {
        return requestTemplate -> {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.getCredentials() instanceof String token) {
                requestTemplate.header("Authorization", "Bearer " + token);
            }
        };
    }
}