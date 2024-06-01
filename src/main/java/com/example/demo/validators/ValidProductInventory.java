package com.example.demo.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ProductInventoryValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidProductInventory {
    String message() default "Invalid inventory: select a value between the min and max values.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
