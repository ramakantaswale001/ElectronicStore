package com.bikkadit.electronic.store.ElectronicStore.validate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

public class ImageNameValidator implements ConstraintValidator<ImageNameValid,String> {

    Logger logger = LoggerFactory.getLogger(ImageNameValidator.class);

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        logger.info("Message from isValid : {} ",value);
    // logic
        if(value.isBlank()){
            return false;
        }else {

            return true;
        }
    }
}
