package dev.itboot.mb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import dev.itboot.mb.repository.TeacherMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class TeacherController {

    private final TeacherMapper mapper;

    @GetMapping("/")
    public String getAllTeachers(Model model) {
        model.addAttribute("page", mapper.selectAll());

        // list.htmlを呼び出す
        return "list";
    }
}
