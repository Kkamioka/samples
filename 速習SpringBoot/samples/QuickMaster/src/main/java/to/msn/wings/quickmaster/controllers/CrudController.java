package to.msn.wings.quickmaster.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import to.msn.wings.quickmaster.common.TitleValidator;
import to.msn.wings.quickmaster.models.Book;
import to.msn.wings.quickmaster.models.BookRepository;

@RequiredArgsConstructor
@RequestMapping("/crud")
@Controller
public class CrudController {
    private final BookRepository rep;
    private final TitleValidator validator;
    
    @InitBinder
    public void initBinder(WebDataBinder binder) {
         binder.addValidators(validator);
    }

    @GetMapping("/create")
    public String create(@ModelAttribute Book book, Model model) {
        model.addAttribute("main", "crud/create::main");
        return "common/layout";
    }
  
    @PostMapping("/save")
    public String save(@Validated @ModelAttribute Book book,
        BindingResult result, Model model,RedirectAttributes attrs) {
        if (result.hasErrors()) {
            model.addAttribute("main", "crud/create::main");
            return "common/layout";
        }
        rep.save(book);
        attrs.addFlashAttribute("success", "データの登録に成功しました。"); 
        return "redirect:/list";
    }
  
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model) {
        Book b = rep.findById(id).get();
        model.addAttribute("book", b);    
        model.addAttribute("main", "crud/edit::main");
        return "common/layout";
    }
  
    @PatchMapping("/update/{id}")
    public String update(@PathVariable int id,
       @ModelAttribute Book book,
       Model model, RedirectAttributes attrs) {
       book.setId(id);
       rep.save(book);
       attrs.addFlashAttribute("success", "データの更新に成功しました。");
       return "redirect:/list";
    }
  
    @GetMapping("/show/{id}")
    public String show(@PathVariable int id, Model model) {
        Book b = rep.findById(id).get();
        model.addAttribute("book", b);
        model.addAttribute("main", "crud/show::main");
        return "common/layout";
    }
  
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable int id, RedirectAttributes attrs) {
        rep.deleteById(id);
        attrs.addFlashAttribute("success", "データの削除に成功しました。");
        return "redirect:/list";
    }
}
