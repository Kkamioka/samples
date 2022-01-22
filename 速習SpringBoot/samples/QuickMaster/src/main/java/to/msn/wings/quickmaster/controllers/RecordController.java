package to.msn.wings.quickmaster.controllers;

//import java.time.LocalDate;
//import java.util.Collection;
//import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import to.msn.wings.quickmaster.models.Book;
import to.msn.wings.quickmaster.models.BookRepository;

@RequiredArgsConstructor
@RequestMapping("/record")
@Controller
public class RecordController {
    private final BookRepository rep; 

    @GetMapping("/find/{id}")
    public String find(@PathVariable int id, Model model) {
        model.addAttribute("book", rep.findById(id));
        model.addAttribute("main", "record/find::main");
        return "common/layout";
    }
  
    @GetMapping("/findby")
    public String findby(Model model) {
        model.addAttribute("books", rep.findByPublisher("翔泳社"));
        //model.addAttribute("books", rep.findByPriceGreaterThan(3000));
        //model.addAttribute("books", rep.findByTitleLike("%入門%"));
        //model.addAttribute("books", rep.findByTitleContaining("入門"));
        //model.addAttribute("books",
        //    rep.findByPublisherIn(List.of("翔泳社", "日経BP")));
        //model.addAttribute("books",
        //    rep.findByPriceBetween(3000, 3500));
        //model.addAttribute("books", rep.findByTitleIsNull());
        //model.addAttribute("books", rep.findByPublishedBefore(LocalDate.now()));
        //model.addAttribute("books",rep.findByPublisherNot("翔泳社"));
        //model.addAttribute("books", rep.findByPublisherAndPriceGreaterThanEqual("翔泳社", 3000));
        //model.addAttribute("books", rep.findByPublisherOrderByPublishedDesc("翔泳社"));
        //model.addAttribute("book", rep.findTopByPublisherOrderByPublishedDesc("翔泳社"));
        //model.addAttribute("books", rep.findTop3ByPublisherOrderByPublishedDesc("翔泳社"));
        //model.addAttribute("books", rep.findByPrice(3000));
        model.addAttribute("main", "record/list::main");
        return "common/layout";
    }
  
    @GetMapping("/first")
    public String first(Model model) {
        model.addAttribute("book",
        rep.findTopByPublisherOrderByPublishedDesc("翔泳社"));
        model.addAttribute("main", "record/find::main");
        return "common/layout";
    }
  
    @GetMapping("/findby2")
    public String findby2(Model model) {
         //model.addAttribute("books", rep.findByPublisherOrderByPrice("翔泳社"));
        model.addAttribute("books", rep.findDistinctByPublisher("翔泳社"));
        model.addAttribute("main", "record/minilist::main");
        return "common/layout";
    }
    
    @GetMapping("/paging")
    public String paging(Model model,
        @PageableDefault(page = 0, size = 3, sort = { "isbn" }) Pageable pageable) {
        Page<Book> page = rep.findByPublisher("翔泳社", pageable);
        model.addAttribute("page", page);
        model.addAttribute("main", "record/paging::main");
        return "common/layout";
    }
  
    @GetMapping("/group")
    public String group(Model model) {
        model.addAttribute("books", rep.groupByPublisher());
        model.addAttribute("main", "record/glist::main");
        return "common/layout";
    }
  
    @GetMapping("/rel")
    public String rel(Model model) {
        model.addAttribute("book", rep.findById(1).get());
        model.addAttribute("main", "record/rel::main");
        return "common/layout";
    }
  

}
