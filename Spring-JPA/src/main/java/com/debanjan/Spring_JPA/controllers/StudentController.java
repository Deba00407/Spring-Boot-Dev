package com.debanjan.Spring_JPA.controllers;

import com.debanjan.Spring_JPA.dto.StudentDTO;
import com.debanjan.Spring_JPA.entities.StudentEntity;
import com.debanjan.Spring_JPA.repos.StudentRepo;
import com.debanjan.Spring_JPA.utils.Logger;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentController {
    private final StudentRepo studentRepo;

    public StudentController(StudentRepo studentRepo) {
        this.studentRepo = studentRepo;
    }

    @GetMapping("/students")
    public List<StudentDTO> getAllStudents() {
        List<StudentEntity> students = studentRepo.findAll();

        return students.stream()
                .map(this::mapToDTO)
                .toList();
    }

    private StudentDTO mapToDTO(StudentEntity entity) {
        StudentDTO dto = new StudentDTO();
        dto.setName(entity.getName());
        dto.setAge(entity.getAge());
        dto.setGender(entity.getGender());
        dto.setStandard(entity.getStandard());
        return dto;
    }


    @GetMapping("/student")
    public StudentEntity getStudentById(@RequestParam(required = false) Long id, @RequestParam String name){
        if(name != null){
            return studentRepo.findByName(name);
        }
        return studentRepo.findById(id).orElse(null);
    }

    @PostMapping("/new-student")
    public String addStudent(@RequestBody StudentDTO newStudent){
        try{
            StudentEntity studentEntity = new StudentEntity();
            studentEntity.setName(newStudent.getName());
            studentEntity.setAge(newStudent.getAge());
            studentEntity.setGender(newStudent.getGender());
            studentEntity.setStandard(newStudent.getStandard());

            studentRepo.save(studentEntity);
        }catch (Exception e){
            return e.getMessage();
        }finally {
            System.out.println("New student added:");
            Logger.logEntity(newStudent);
        }

        return "Student added successfully!";
    }

    @DeleteMapping("/delete-student/{id}")
    public String deleteStudent(@PathVariable Long id){
        try{
            studentRepo.deleteById(id);
        }catch (Exception e){
            String errorMsg = e.getMessage();
            return String.format("Error deleting student: %s", errorMsg);
        }
        return "Student deleted successfully!";
    }
}
