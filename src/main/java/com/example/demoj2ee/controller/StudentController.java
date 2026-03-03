package com.example.demoj2ee.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.example.demoj2ee.model.Student;

@Controller
public class StudentController {
    @GetMapping("/student")
    public String getStudent(Model model) {
        Student student = new Student(1, "Ngân");
        model.addAttribute("student", student);
        model.addAttribute("message", "Hello Student");
        return "student";
    }
}
