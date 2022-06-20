package inventory.model.validation;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.IOException;
import java.util.Objects;

public class FileValidator implements ConstraintValidator<FileSize, MultipartFile> {

    private long size;

    @Override
    public void initialize(FileSize constraintAnnotation) {
        size = constraintAnnotation.value().maxSize;
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        try {
            boolean valid = true;

            if (file.getBytes().length > size) {
                context.buildConstraintViolationWithTemplate(
                        "File size is exceed limit: " + size).addConstraintViolation();
                valid = false;
            }

            if (!Objects.requireNonNull(file.getOriginalFilename()).endsWith("jpg")) {
                context.buildConstraintViolationWithTemplate(
                        "Incompatible file type " + file.getOriginalFilename()).addConstraintViolation();
                valid = false;
            }

            return valid;
        } catch (IOException | NullPointerException e) {
            context.buildConstraintViolationWithTemplate("Can't read file: ").addConstraintViolation();
            return false;
        }
    }
}
