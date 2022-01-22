package com.example.todolist.controller;

import java.util.Locale;
import javax.servlet.http.HttpSession;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.todolist.common.OpMsg;
import com.example.todolist.entity.Account;
import com.example.todolist.form.LoginData;
import com.example.todolist.form.RegistData;
import com.example.todolist.repository.AccountRepository;
import com.example.todolist.service.LoginService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final AccountRepository accountRepository;
    private final LoginService loginService;
    private final MessageSource messageSource;
    private final HttpSession session;

    // Login画面表示
    @GetMapping("/")
    public ModelAndView showLogin(ModelAndView mv) {
        mv.setViewName("loginForm");
        mv.addObject("loginData", new LoginData());
        return mv;
    }

    @GetMapping("/login")
    public ModelAndView login(ModelAndView mv) {
        mv.setViewName("loginForm");
        mv.addObject("loginData", new LoginData());
        return mv;
    }

    // ログインボタン押下
    @PostMapping("/login/do")
    public String login(@ModelAttribute @Validated LoginData loginData, BindingResult result,
                        Model model, RedirectAttributes redirectAttributes, Locale locale) {
        // バリデーション
        if (result.hasErrors()) {
            // エラーあり -> エラーメッセージをセット
            String msg = messageSource.getMessage("msg.e.input_something_wrong", null, locale);
            model.addAttribute("msg", new OpMsg("E", msg));
            return "loginForm";
        }

        // サービスでチェック
        if (!loginService.isValid(loginData, result, locale)) {
            // エラーあり -> エラーメッセージをセット
            String msg = messageSource.getMessage("msg.e.input_something_wrong", null, locale);
            model.addAttribute("msg", new OpMsg("E", msg));
            return "loginForm";
        }

        // (念のため)セッション情報をクリアする
        session.invalidate();

        // LoginしたユーザーのaccountIdをセッションへ格納する
        Account account = accountRepository.findByLoginId(loginData.getLoginId()).get();
        session.setAttribute("accountId", account.getId());

        // Login成功メッセージをセットしてリダイレクト
        String msg = messageSource
            .getMessage("msg.i.login_successful",
                new Object[] { account.getLoginId(), account.getName() }, locale);
        redirectAttributes.addFlashAttribute("msg", new OpMsg("I", msg));
        return "redirect:/todo";
    }

    // Logout処理
    @GetMapping("/logout")
    public String logout(RedirectAttributes redirectAttributes, Locale locale) {
        // セッション情報をクリアする
        session.invalidate();

        // Logout完了メッセージをセットしてリダイレクト
        String msg = messageSource.getMessage("msg.i.logout_successful", null, locale);
        redirectAttributes.addFlashAttribute("msg", new OpMsg("I", msg));
        return "redirect:/";
    }
    
    // ユーザー新規登録 - 画面表示
    @GetMapping("/regist")
    public ModelAndView showRegist(ModelAndView mv) {
        mv.setViewName("registForm");
        mv.addObject("registData", new RegistData());
        return mv;
    }

    // ユーザー新規登録 - 登録
    @PostMapping("/regist/do")
    public String registNewUser(@ModelAttribute @Validated RegistData registData,
                                 BindingResult result, 
                                 Model model,
                                 RedirectAttributes redirectAttributes, 
                                 Locale locale) {
        // エラーチェック
        boolean isValid = loginService.isValid(registData, result, locale);
        if (!result.hasErrors() && isValid) {
            // エラーなし -> 登録
            Account account = registData.toEntity();
            accountRepository.saveAndFlush(account);

            // 登録完了メッセージをセットしてリダイレクト
            String msg = messageSource.getMessage(
                "msg.i.regist_successful", 
                new Object[] { account.getName(), account.getLoginId()  },
                locale);
            redirectAttributes.addFlashAttribute("msg", new OpMsg("I", msg));
            return "redirect:/";

        } else {
            // エラーあり -> エラーメッセージをセット
            String msg 
              = messageSource.getMessage("msg.e.input_something_wrong", null, locale);
            model.addAttribute("msg", new OpMsg("E", msg));
            return "registForm";
        }
    }

    // ユーザー新規登録 - 戻る
    @GetMapping("/regist/cancel")
    public String registCancel() {
        return "redirect:/";
    }


}
