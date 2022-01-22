package to.msn.wings.quickmaster.common;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Min;

@Documented
@Constraint(validatedBy = {})
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@ReportAsSingleViolation
@Min(3000)
public @interface IsHigher {
    String message() default "{0}は3000以上にしてください。";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
