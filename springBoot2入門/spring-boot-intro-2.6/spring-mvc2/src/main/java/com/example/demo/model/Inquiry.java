package com.example.demo.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Inquiry {

    // 必須入力、文字列が60文字まで
    @NotBlank
    @Size(max = 60)
    private String name;

    // 必須入力、Email形式であること、文字列が254文字まで
    @NotBlank
    @Email
    @Size(max = 254)
    private String email;

    // 必須入力、文字列が500文字まで
    @NotBlank
    @Size(max = 500)
    private String inquiry;
}
