package com.ref.api.validators;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Pattern;

@Documented
@Constraint(validatedBy = {})
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Pattern(regexp = ".+@.+\\.[a-z]+")
@ReportAsSingleViolation
public @interface Email {

	public abstract String message() default "{package.name.Email.message}";

	public abstract Class<?>[] groups() default {};

	public abstract Class<? extends Payload>[] payload() default {};

}