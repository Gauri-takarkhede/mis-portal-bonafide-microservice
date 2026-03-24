package com.studentmisportal.bonafide_service.entity;

import com.studentmisportal.bonafide_service.entity.type.BonafideStatusType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "bonafide")
public class Bonafide {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String studentMis;

    @Column(nullable = false)
    private String studentName;

    @Column(nullable = false)
    private String reason;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BonafideStatusType status;

    private String approvedBy;

    private String rejectedBy;

    private LocalDate approvedDate;

    private LocalDate rejectedDate;


    @Column(nullable = false)
    private LocalDate requestedDate;
}