package inventory.model.validation;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.IOException;
import java.util.Objects;
import java.util.Set;

public class FileValidator implements ConstraintValidator<FileSize, MultipartFile> {

    private final Set<String> extensions = Set.of("jpg", "jpeg", "bmp", "png");

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

            String filename = Objects.requireNonNull(file.getOriginalFilename());
            if (extensions.stream().noneMatch(filename::endsWith)) {
                context.buildConstraintViolationWithTemplate(
                        "Incompatible file type!").addConstraintViolation();
                valid = false;
            }

            return valid;
        } catch (IOException | NullPointerException e) {
            context.buildConstraintViolationWithTemplate("Can't read file: ").addConstraintViolation();
            return false;
        }
    }
}
