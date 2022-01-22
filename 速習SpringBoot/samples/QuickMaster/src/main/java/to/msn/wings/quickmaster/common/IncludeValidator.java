package to.msn.wings.quickmaster.common;

import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IncludeValidator implements ConstraintValidator<Include, String> {
  private List<String> list;
  
  @Override
  public void initialize(Include anno) {
    this.list = Arrays.asList(anno.list());
  }
  
  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value == null) {
      return true;
    }
    return this.list.contains(value);
  }
}
