package to.msn.wings.quickmaster.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    @Size(max=30)
    @Column(name = "username", nullable = true)
    private String username;

    @NotBlank
    @Size(max=255)
    @Column(name = "password", nullable = true)
    private String password;
    
    @Column(name = "enabled")
    private boolean enabled = true;
}
