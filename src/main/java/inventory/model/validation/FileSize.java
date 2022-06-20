package inventory.model.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static inventory.model.validation.FileSize.BlobType.TINYBLOB;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = FileValidator.class)
public @interface FileSize {

    String message() default "File size is exceed limit";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    BlobType value() default TINYBLOB;


    enum BlobType {

        TINYBLOB(255), BLOB(65535), MEDIUMBLOB(16_777_215), LONGBLOB(4_294_967_295L);

        final long maxSize;

        BlobType(long maxSize) {
            this.maxSize = maxSize;
        }
    }

}
