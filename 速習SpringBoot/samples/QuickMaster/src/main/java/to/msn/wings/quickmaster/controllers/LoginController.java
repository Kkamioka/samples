package to.msn.wings.quickmaster.controllers;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String index(Model model) {
        model.addAttribute("main", "login/index::main");
        return "common/layout";
    }
    
    @GetMapping({"/password", "/password/{pass}"})
    @ResponseBody
    public String password(@RequestParam(defaultValue = "12345") String pass) {
         BCryptPasswordEncoder enc = new BCryptPasswordEncoder() ;
         return enc.encode(pass);
    }
}
