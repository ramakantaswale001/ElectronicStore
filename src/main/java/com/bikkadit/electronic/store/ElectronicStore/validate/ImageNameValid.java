package com.bikkadit.electronic.store.ElectronicStore.validate;

import javax.persistence.Table;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.awt.*;
import java.lang.annotation.*;
import java.lang.reflect.Field;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ImageNameValidator.class)
public @interface ImageNameValid {

    // error message
    String message() default "Invalid Image Name !!!";

    // represent group of contraints
    Class<?>[] groups() default { };

    //additional information about annotation
    Class<? extends Payload>[] payload() default { };

}
