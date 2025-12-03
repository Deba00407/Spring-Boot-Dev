package com.debanjan.Spring_JPA.controllers;

import com.debanjan.Spring_JPA.dto.StudentDTO;
import com.debanjan.Spring_JPA.services.StudentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

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
    public String addStudent(@RequestBody @Valid StudentDTO newStudent){
        return studentService.addStudent(newStudent);
    }

    @DeleteMapping("/delete-student/{id}")
    public String deleteStudent(@PathVariable Long id){
        return studentService.deleteStudent(id);
    }

    @PatchMapping("/update-student")
    public String updateStudent(@RequestParam Long id, @RequestParam String field, @RequestParam String value){
        return studentService.updateStudent(id, field, value);
    }
}
