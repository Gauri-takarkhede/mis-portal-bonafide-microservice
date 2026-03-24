package com.studentmisportal.bonafide_service.repository;
import com.studentmisportal.bonafide_service.entity.Bonafide;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BonafideRepository extends JpaRepository<Bonafide, Long> {

     List<Bonafide> findByStudentMis(String mis);
}
