package to.msn.wings.quickmaster.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import to.msn.wings.quickmaster.models.Book;
import to.msn.wings.quickmaster.models.BookRepository;

@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class ApiController {
    private final BookRepository rep;
  
    @GetMapping("/list")
    public List<Book> list() {
        return rep.findAll();
    }
}