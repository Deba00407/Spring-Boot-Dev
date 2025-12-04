package com.debanjan.Spring_JPA.controllers;

import com.debanjan.Spring_JPA.dto.StudentDTO;
import com.debanjan.Spring_JPA.services.StudentService;
import com.debanjan.Spring_JPA.utils.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/students")
    public List<StudentDTO> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/student")
    public StudentDTO getStudentById(@RequestParam Long id){
        return studentService.getStudentById(id);
    }

    @PostMapping("/new-student")
    public ResponseEntity<ApiResponse<String>> addStudent(@RequestBody @Valid StudentDTO newStudent){
        String msg = studentService.addStudent(newStudent);

        ApiResponse<String> response = ApiResponse.<String>builder()
                .data(msg)
                .error(null)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete-student/{id}")
    public ResponseEntity<ApiResponse<String>> deleteStudent(@PathVariable Long id){
        String msg = studentService.deleteStudent(id);

        ApiResponse<String> response = ApiResponse.<String>builder()
                .data(msg)
                .error(null)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/update-student")
    public ResponseEntity<ApiResponse<String>> updateStudent(@RequestParam Long id, @RequestParam String field, @RequestParam String value){
        String msg = studentService.updateStudent(id, field, value);

        ApiResponse<String> response = ApiResponse.<String>builder()
                .data(msg)
                .error(null)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.ok(response);
    }
}
