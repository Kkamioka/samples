package to.msn.wings.quickmaster.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;
import to.msn.wings.quickmaster.models.BookRepository;

@RequiredArgsConstructor
@Controller
public class ViewController {
    private final BookRepository rep;

    @GetMapping("/view/html")
    public String html(Model model) {
        model.addAttribute("message",
            "<h1>こんにちは</h1>" + "<span><a href='https://spring.io/projects/spring-boot/'>Spring Boot!!</a></span>");
        return "view/html";
    }

    @GetMapping("/view/format")
    public String format(Model model) {
         model.addAttribute("name", "山田");
         return "view/format";
    }

    @GetMapping("/view/attr")
    public String attr(Model model) {
         model.addAttribute("url", "https://wings.msn.to/");
         return "view/attr";
    }

    @GetMapping("/view/link")
    public String link() {
        return "view/link";
    }

    @GetMapping("/view/bool")
    public String bool() {
         return "view/bool";
    }

    @GetMapping("/view/classappend")
    public String classappend() {
        return "view/classappend";
    }

    @GetMapping("/view/alttitle")
    public String alttitle() {
         return "view/alttitle";
    }

    @GetMapping("/view/list")
    public String list(Model model) {
         model.addAttribute("books", rep.findAll());
        return "view/list";
    }

    @GetMapping("/view/list_switch")
    public String list_switch(Model model) {
         model.addAttribute("books", rep.findAll());
         return "view/list_switch";
    }

    @GetMapping("/view/list_condition")
    public String list_condition(Model model) {
        model.addAttribute("books", rep.findAll());
         return "view/list_condition";
    }

    @GetMapping("/view/list_elvis")
    public String list_elvis(Model model) {
         model.addAttribute("books", rep.findAll());
         return "view/list_elvis";
    }

    @GetMapping("/view/each_status")
    public String each_status(Model model) {
         model.addAttribute("weeks", List.of("月", "火", "水", "木", "金", "土", "日"));
         return "view/each_status";
    }

    @GetMapping("/view/each_seq")
    public String each_seq() {
        return "view/each_seq";
    }
  
    @GetMapping("/view/each_block")
    public String each_block(Model model) {
         model.addAttribute("books", rep.findAll());
         return "view/each_block";
    }
  
    @GetMapping("/view/fragment")
    public String fragment(Model model) {
        model.addAttribute("msg", "こんにちは、世界！");
        return "view/fragment";
    }
  
    @GetMapping("/view/fragment_id")
    public String fragment_id(Model model) {
         model.addAttribute("msg", "こんにちは、世界！");
         return "view/fragment_id";
    }
  
    @GetMapping("/view/fragment_param")
    public String fragment_param(Model model) {
        model.addAttribute("msg", "こんにちは、世界！");
        return "view/fragment_param";
    }
  
    @GetMapping("/view/fragment_meta")
    public String fragment_meta(Model model) {
        model.addAttribute("msg", "こんにちは、世界！");
        return "view/fragment_meta";
    }
  
    @GetMapping("/view/master")
    public String master(Model model) {
        model.addAttribute("title", "共通レイアウト");
        model.addAttribute("lib", "view/master::lib");
        model.addAttribute("main", "view/master::main");
       return "common/layout";
    }
  
    @GetMapping("/view/i18n")
    public String i18n(Model model) {
         model.addAttribute("main", "view/i18n::main");
         return "common/layout";
    }
  
    @GetMapping("/view/comment")
    public String comment(Model model) {
        model.addAttribute("main", "view/comment:main");
        return "view/comment";
    }
  
    @GetMapping("/view/proto")
    public String proto(Model model) {
        model.addAttribute("main", "view/proto::main");
        return "view/proto";
    }
}
