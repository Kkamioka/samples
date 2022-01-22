package dev.itboot.mb.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Teacher {
    private Long id;

    // Validationを追加
    @NotBlank
    @Size(max = 60)
    private String userName;

    // Validationを追加
    @NotBlank
    @Email
    @Size(max = 254)
    private String email;
}
