package com.studentmisportal.bonafide_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtPayloadDto {
    String role;
    String mis;
    String username;

}
