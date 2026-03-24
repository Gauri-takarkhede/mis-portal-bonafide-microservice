package com.studentmisportal.bonafide_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BonafideRequestDto {
    private String mis;
    private String username;
    private String reason;
}
