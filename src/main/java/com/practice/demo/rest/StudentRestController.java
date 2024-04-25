package com.practice.demo.rest;

import com.practice.demo.entity.Student;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentRestController {

    private List<Student> theStudents;
    //define @PostConstruct to load the student data.. only once

    @PostConstruct
    public void loadStudentData(){
        theStudents = new ArrayList<>();
        theStudents.add(new Student("Pranjul", "Singh"));
        theStudents.add(new Student("Rahul", "Yadav"));
        theStudents.add(new Student("Hitesh", "Kumar"));
    }

    //define endpoint "/student" -> return the list of the students

    @GetMapping("/students")
    public List<Student> getStudents(){

        return theStudents;
    }

    //define endpoint "/students/{studentId} -> return student at index

    @GetMapping("/students/{studentId}")
    public Student getStudent(@PathVariable int studentId){
        //check the studentId against the list size

        if((studentId>=theStudents.size() || studentId<0)){
            throw new StudentNotFoundException("Student Id " + studentId + " not found");
        }
        return theStudents.get(studentId);
    }

    //Add an exception handler using @ExceptionHandler
    @ExceptionHandler
    public ResponseEntity<StudentErrorResponse> handleException(StudentNotFoundException exc){

        //create a StudentErrorResponse
        StudentErrorResponse error= new StudentErrorResponse();
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(exc.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        //return ResponseEntity
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    //add another exception handler.. to catch any exception

    @ExceptionHandler
    public ResponseEntity<StudentErrorResponse> handleException(Exception exc){

        //create a StudentErrorResponse
        StudentErrorResponse error= new StudentErrorResponse();
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage(exc.getMessage());
        // to set custom message just write your custom message
        // error.setMessage("Characters are not allowed");

        error.setTimeStamp(System.currentTimeMillis());

        //return ResponseEntity
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
