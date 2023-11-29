package com.example.studentdetail.controller;

import com.example.studentdetail.StudentApi;
import com.example.studentdetail.mode.dto.StudentDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentDetailController {

    private final StudentApi studentApi;

    public StudentDetailController(StudentApi studentApi) {
        this.studentApi = studentApi;
    }


    @GetMapping("/get-firate-university")
    public ResponseEntity getOracleStudent(){

        StudentDto studentDto = StudentDto.builder().schoolName("FIRAT UNIVERSITY")
                .budget("8000")
                .students(studentApi.getFindByFiratUniversity()).build();

        return ResponseEntity.ok().body(studentDto);
    }

    @GetMapping("/get-istanbul-university")
    public ResponseEntity getEclipseStudent(){

        StudentDto studentDto = StudentDto.builder().schoolName("ISTANBUL UNIVERSITY")
                .budget("3500")
                .students(studentApi.getFindByIstanbulUniversity()).build();

        return ResponseEntity.ok().body(studentDto);
    }

}
