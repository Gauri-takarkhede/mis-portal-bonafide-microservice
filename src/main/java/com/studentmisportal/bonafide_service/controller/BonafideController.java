package com.studentmisportal.bonafide_service.controller;

import com.studentmisportal.bonafide_service.dto.ApiResponseDto;
import com.studentmisportal.bonafide_service.dto.BonafideRequestDto;
import com.studentmisportal.bonafide_service.dto.BonafideResponseDto;
import com.studentmisportal.bonafide_service.service.BonafideService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/bonafide")
@RequiredArgsConstructor
public class BonafideController {
    private final BonafideService bonafideService;
//    private final UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<BonafideResponseDto>> getAllBonafides(){
        List<BonafideResponseDto> bonafides = bonafideService.getAllBonafides();
        return ResponseEntity.ok(bonafides);
    }

//    @GetMapping("/")
//    public ResponseEntity<List<BonafideResponseDto>> getBonafide(HttpServletRequest request){
//        List<BonafideResponseDto> bonafides = bonafideService.getBonafides(userService.getMis(request));
//        return ResponseEntity.ok(bonafides);
//    }
//
    @PostMapping("/")
    public ResponseEntity<ApiResponseDto> getBonafide(@Valid  @RequestBody BonafideRequestDto bonafideRequestDto){
        String response =  bonafideService.createBonafideRequest(bonafideRequestDto);
        return ResponseEntity.ok(new ApiResponseDto(response));
    }
//
//    @PreAuthorize("hasRole('FACULTY')")
//    @PatchMapping("/approve/{id}")
//    public ResponseEntity<ApiResponseDto> approveBonafide(@PathVariable Long id, HttpServletRequest request){
//        return ResponseEntity.ok(new ApiResponseDto(bonafideService.approveBonafide(id, userService.getUserName(request))));
//    }
//
//    @PreAuthorize("hasRole('FACULTY')")
//    @PatchMapping("/reject/{id}")
//    public ResponseEntity<ApiResponseDto> rejectBonafide(@PathVariable Long id, HttpServletRequest request){
//        return ResponseEntity.ok(new ApiResponseDto(bonafideService.rejectBonafide(id, userService.getUserName(request))));
//    }
//
//    @GetMapping("/download/{id}")
//    public ResponseEntity<byte[]> downloadBonafide(@PathVariable Long id) throws IOException {
//
//        byte[] pdfBytes = bonafideService.generateBonafide(id);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_PDF);
//        headers.setContentDisposition(
//                ContentDisposition.attachment()
//                        .filename("bonafide_certificate.pdf")
//                        .build()
//        );
//
//        return ResponseEntity.ok()
//                .headers(headers)
//                .body(pdfBytes);
//    }
}
