package com.studentmisportal.bonafide_service.dto;
import com.studentmisportal.bonafide_service.entity.type.CourseType;
import com.studentmisportal.bonafide_service.entity.type.DepartmentType;
import com.studentmisportal.bonafide_service.entity.type.RoleType;
import lombok.Data;

@Data
public class UserDto {
    private String username;
    private String password;
    private String email;
    private String phone;
    private String mis;
    private RoleType role;
    private DepartmentType department;
    private StudentDetailsDto studentDetails;
}
