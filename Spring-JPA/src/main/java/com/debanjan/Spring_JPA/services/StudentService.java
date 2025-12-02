package com.debanjan.Spring_JPA.services;

import com.debanjan.Spring_JPA.dto.StudentDTO;
import com.debanjan.Spring_JPA.entities.StudentEntity;
import com.debanjan.Spring_JPA.repos.StudentRepo;
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
        StudentEntity studentEntity = studentRepo.findById(id).orElseThrow();
        return modelMapper.map(studentEntity, StudentDTO.class);
    }

    public String addStudent(StudentDTO newStudent){
        try{
            StudentEntity studentEntity = modelMapper.map(newStudent, StudentEntity.class);
            studentRepo.save(studentEntity);
        }catch (Exception e){
            return e.getMessage();
        }finally {
            System.out.println("New student added:");
            Logger.logEntity(newStudent);
        }

        return "Student added successfully!";
    }

    public String deleteStudent(Long id){
        try{
            studentRepo.deleteById(id);
        }catch (Exception e){
            String errorMsg = e.getMessage();
            return String.format("Error deleting student: %s", errorMsg);
        }
        return "Student deleted successfully!";
    }

    public String updateStudent(Long id, String field, String value) {
        field = field.toLowerCase();

        StudentEntity student;
        try {
            student = studentRepo.findById(id).orElseThrow(
                    () -> new RuntimeException("Student not found with id: " + id)
            );
        } catch (Exception e) {
            return String.format("Error fetching student: %s", e.getMessage());
        }

        try {
            switch (field) {
                case "name":
                    student.setName(value);
                    break;

                case "age":
                    student.setAge(Integer.parseInt(value));
                    break;

                case "standard":
                    student.setStandard(value);
                    break;

                case "gender":
                    student.setGender(value);
                    break;

                default:
                    return "Invalid field: " + field;
            }

            // save the updated student
            studentRepo.save(student);

        } catch (Exception e) {
            return String.format("Error updating student: %s", e.getMessage());
        }

        return "Student updated successfully!";
    }
}
