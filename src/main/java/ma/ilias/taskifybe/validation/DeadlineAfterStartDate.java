package ma.ilias.taskifybe.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = DeadlineAfterStartDateValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DeadlineAfterStartDate {
    String message() default "(be) Deadline must be after the start date";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
