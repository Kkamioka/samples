package to.msn.wings.quickmaster.models;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberForm {
    private String name;
    
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate birth;
    
    private String email;
}
