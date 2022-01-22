package to.msn.wings.quickmaster.common;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import to.msn.wings.quickmaster.models.Book;

@Component
public class TitleValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.isAssignableFrom(clazz);
    }
  
    @Override
    public void validate(Object target, Errors errors) {
        Book model = (Book)target;
        String title = model.getTitle();
        String publisher = model.getPublisher();
        if (title.isBlank() || publisher.isBlank()) {
            return;
        }
        if (publisher.equals("翔泳社") && !title.startsWith("独習")) {
            errors.rejectValue("title","TitleValidator.title",
                "翔泳社の書籍はタイトルが「独習～」で始まらなければなりません。");
        }
    }
}