package dev.itboot.mb.config;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import dev.itboot.mb.model.Teacher;
import dev.itboot.mb.service.TeacherService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class DataLoader implements ApplicationRunner {

    private final TeacherService service;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        for (int i = 0; i < 5; i++) {
            var teacher = new Teacher();
            teacher.setUserName("テスト");
            teacher.setEmail("test@example.com");
            service.save(teacher);
        }
    }
}
