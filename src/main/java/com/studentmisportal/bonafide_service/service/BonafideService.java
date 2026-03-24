package com.studentmisportal.bonafide_service.service;


import com.studentmisportal.bonafide_service.dto.BonafideRequestDto;
import com.studentmisportal.bonafide_service.dto.BonafideResponseDto;
import com.studentmisportal.bonafide_service.dto.UserDto;
import com.studentmisportal.bonafide_service.entity.Bonafide;
import com.studentmisportal.bonafide_service.entity.type.BonafideStatusType;
import com.studentmisportal.bonafide_service.feign.StudentServiceClient;
import com.studentmisportal.bonafide_service.repository.BonafideRepository;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BonafideService {

    private final BonafideRepository bonafideRepository;
    private final ModelMapper modelMapper;
    private final StudentServiceClient studentServiceClient;
//    private final UserRepository userRepository;

    public List<BonafideResponseDto> getAllBonafides(){
        List<Bonafide> allBonafides = bonafideRepository.findAll();

        return allBonafides.stream()
                .map(bonafide-> modelMapper.map(bonafide, BonafideResponseDto.class))
                .toList();
    }

    public List<BonafideResponseDto> getBonafides(String mis){
        List<Bonafide> allBonafides = bonafideRepository.findByStudentMis(mis);

        return allBonafides.stream()
                .map(bonafide-> modelMapper.map(bonafide, BonafideResponseDto.class))
                .toList();
    }

    public String createBonafideRequest(BonafideRequestDto bonafiderequest) {

        Bonafide bonafide = new Bonafide();
        bonafide.setStudentMis(bonafiderequest.getMis());
        bonafide.setStudentName(bonafiderequest.getUsername());
        bonafide.setReason(bonafiderequest.getReason());
        bonafide.setRequestedDate(LocalDate.now());
        bonafide.setStatus(BonafideStatusType.PENDING);
        bonafide.setApprovedBy(null);
        bonafide.setApprovedDate(null);

        bonafideRepository.save(bonafide);

        return "Bonafide Created";
    }
//
//    public String approveBonafide(Long id, String facultyName) {
//        Bonafide bonafide = bonafideRepository.findById(id).orElseThrow(()-> new UsernameNotFoundException("User not found"));
//        bonafide.setStatus(BonafideStatusType.APPROVED);
//        bonafide.setApprovedDate(LocalDate.now());
//        bonafide.setApprovedBy(facultyName);
//        bonafideRepository.save(bonafide);
//        return "Bonafide Approved";
//    }
//
//    public String rejectBonafide(Long id, String facultyName) {
//        Bonafide bonafide = bonafideRepository.findById(id).orElseThrow(()-> new UsernameNotFoundException("User not found"));
//        bonafide.setStatus(BonafideStatusType.REJECTED);
//        bonafide.setRequestedDate(LocalDate.now());
//        bonafide.setRejectedBy(facultyName);
//        bonafideRepository.save(bonafide);
//        return "Bonafide Rejected";
//    }
//
//    public byte[] generateBonafide(Long id) throws IOException {
//
//        Bonafide bonafide = bonafideRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Request not found"));
//
//        if (!"approved".equalsIgnoreCase(bonafide.getStatus().name())) {
//            throw new RuntimeException("Bonafide not approved yet");
//        }
//
//        User student = userRepository.findByMis(bonafide.getStudentMis())
//                .orElseThrow(() -> new RuntimeException("Student not found"));
//
//        ClassPathResource resource =
//                new ClassPathResource("static/images/bonafide_certificate.pdf");
//
//        PDDocument document = PDDocument.load(resource.getInputStream());
//        PDPage page = document.getPage(0);
//
//        PDPageContentStream contentStream =
//                new PDPageContentStream(document, page,
//                        PDPageContentStream.AppendMode.APPEND, true);
//
//        PDFont font = PDType1Font.HELVETICA;
//
//        contentStream.setNonStrokingColor(0, 0, 0);
//
//        PDRectangle mediaBox = page.getMediaBox();
//        float width = mediaBox.getWidth();
//        float height = mediaBox.getHeight();
//        System.out.println("Page Width: " + mediaBox.getWidth());
//        System.out.println("Page Height: " + mediaBox.getHeight());
//        System.out.println("Username" + student.getUsername());
//
//        // Student Name
//        contentStream.beginText();
//        contentStream.setFont(font, 24);
//        contentStream.newLineAtOffset(width * 0.30f, height * 0.50f);
//        contentStream.showText(student.getUsername());
//        contentStream.endText();
//
//        // Course
//        contentStream.beginText();
//        contentStream.setFont(font, 24);
//        contentStream.newLineAtOffset(width * 0.15f, height * 0.45f);
//        contentStream.showText(student.getStudentDetails().getCourse().name());
//        contentStream.endText();
//
//        // Department
//        contentStream.beginText();
//        contentStream.newLineAtOffset(width * 0.37f, height * 0.45f);
//        contentStream.showText(student.getDepartment().getDepartmentName().name());
//        contentStream.endText();
//
//        // Year
//        contentStream.beginText();
//        contentStream.newLineAtOffset(width * 0.82f, height * 0.45f);
//        contentStream.showText(String.valueOf(LocalDate.now().getYear()));
//        contentStream.endText();
//
//        // MIS
//        contentStream.beginText();
//        contentStream.newLineAtOffset(width * 0.45f, height * 0.39f);
//        contentStream.showText(student.getMis());
//        contentStream.endText();
//
//        // Reason
//        contentStream.beginText();
//        contentStream.newLineAtOffset(width * 0.06f, height * 0.22f);
//        contentStream.showText(bonafide.getReason());
//        contentStream.endText();
//
//        contentStream.close();
//
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        document.save(baos);
//        document.close();
//
//        return baos.toByteArray();
//    }
}
