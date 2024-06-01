package com.example.demo.validators;

import com.example.demo.domain.Product;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ProductInventoryValidator implements ConstraintValidator<ValidProductInventory, Product> {

    @Override
    public void initialize(ValidProductInventory constraintAnnotation) {
    }

    @Override
    public boolean isValid(Product product, ConstraintValidatorContext context) {
        if (product.getMinInv() > product.getMaxInv()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Minimum inventory cannot be greater than maximum inventory.")
                    .addPropertyNode("minInv")
                    .addConstraintViolation();
            return false;
        }

        if (product.getInv() < product.getMinInv() || product.getInv() > product.getMaxInv()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Inventory must be between the minimum and maximum values.")
                    .addPropertyNode("inv")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
