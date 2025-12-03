package com.debanjan.Spring_JPA.services;

import com.debanjan.Spring_JPA.dto.StudentDTO;
import com.debanjan.Spring_JPA.entities.StudentEntity;
import com.debanjan.Spring_JPA.repos.StudentRepo;
import com.debanjan.Spring_JPA.utils.Gender;
import com.debanjan.Spring_JPA.utils.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepo studentRepo;

    private final ModelMapper modelMapper;

    public StudentService(StudentRepo studentRepo, ModelMapper modelMapper) {
        this.studentRepo = studentRepo;
        this.modelMapper = modelMapper;
    }

    public List<StudentDTO> getAllStudents() {
        List<StudentEntity> students = studentRepo.findAll();

        return students.stream()
                .map(student -> modelMapper.map(student, StudentDTO.class))
                .toList();
    }

    public StudentDTO getStudentById(Long id){
        StudentEntity studentEntity = studentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
        return modelMapper.map(studentEntity, StudentDTO.class);
    }

    public String addStudent(StudentDTO newStudent){
        StudentEntity studentEntity = modelMapper.map(newStudent, StudentEntity.class);
        studentRepo.save(studentEntity);

        System.out.println("New student added:");
        Logger.logEntity(newStudent);

        return "Student added successfully!";
    }

    public String deleteStudent(Long id){
        if (!studentRepo.existsById(id)) {
            throw new RuntimeException("Student not found with id: " + id);
        }
        studentRepo.deleteById(id);
        return "Student deleted successfully!";
    }

    public String updateStudent(Long id, String field, String value) {
        field = field.toLowerCase();

        StudentEntity student = studentRepo.findById(id).orElseThrow(
                () -> new RuntimeException("Student not found with id: " + id)
        );

        switch (field) {
            case "name":
                student.setName(value);
                break;

            case "age":
                try {
                    student.setAge(Integer.parseInt(value));
                } catch (NumberFormatException e) {
                    throw new RuntimeException("Invalid age format: " + value);
                }
                break;

            case "standard":
                student.setStandard(value);
                break;

            case "gender":
                try {
                    student.setGender(Gender.valueOf(value.toUpperCase()));
                } catch (IllegalArgumentException ex) {
                    throw new RuntimeException("Invalid gender value. Allowed: MALE, FEMALE, OTHER");
                }
                break;


            default:
                throw new RuntimeException("Invalid field: " + field);
        }

        // save the updated student
        studentRepo.save(student);

        return "Student updated successfully!";
    }
}
