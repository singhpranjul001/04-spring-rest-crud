package com.practice.demo.rest;

import com.practice.demo.entity.Student;
import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return theStudents.get(studentId);
    }
}
