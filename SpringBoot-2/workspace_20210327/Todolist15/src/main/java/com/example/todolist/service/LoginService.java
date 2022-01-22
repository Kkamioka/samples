package com.example.todolist.service;

import java.util.Locale;
import java.util.Optional;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import com.example.todolist.entity.Account;
import com.example.todolist.form.LoginData;
import com.example.todolist.form.RegistData;
import com.example.todolist.repository.AccountRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LoginService {
    private final MessageSource messageSource;
    private final AccountRepository accountRepository;

    // Loginチェック
    public boolean isValid(LoginData loginData, BindingResult result, Locale locale) {
        // ログインIDが登録されているか？
        Optional<Account> account = accountRepository.findByLoginId(loginData.getLoginId());
        if (account.isEmpty()) {
            // 登録されていない
            FieldError fieldError = new FieldError(
                result.getObjectName(),
                "password",
                messageSource.getMessage("NotFound.loginData.loginId", null, locale));
            result.addError(fieldError);
            return false;
        }

        // パスワードチェック
        if (!account.get().getPassword().equals(loginData.getPassword())) {
            // 登録されているものと違う
            FieldError fieldError = new FieldError(
                result.getObjectName(),
                "password",
                messageSource.getMessage("NotFound.loginData.password", null, locale));
            result.addError(fieldError);
            return false;
        }

        return true;
    }
    
    // 登録画面用のチェック
    public boolean isValid(RegistData registData, BindingResult result, Locale locale) {
        if (!registData.getPassword1().equals(registData.getPassword2())) {
            // パスワード不一致
            FieldError fieldError = new FieldError(
                result.getObjectName(),
                "password2",
                messageSource.getMessage("Unmatch.registData.password", null, locale));
            result.addError(fieldError);
            return false;
        }
        
        // ログインIDがすでに使われていないか？
       Optional<Account> account= accountRepository.findByLoginId(registData.getLoginId());
       if (account.isPresent()) {
            // 登録されている => 使われている
            FieldError fieldError = new FieldError(
                result.getObjectName(),
                "loginId",
                messageSource.getMessage("AlreadyUsed.registData.loginId", null, locale));
            result.addError(fieldError);
            return false;
        }

       return true;
    }
}
