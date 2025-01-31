package ma.ilias.taskifybe.validation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.Map;

@ControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        StringBuilder errorMessage = new StringBuilder();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errorMessage.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("\n");
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errorMessage.append("global: ").append(error.getDefaultMessage()).append("\n");
        }
        if (!errorMessage.isEmpty()) {
            errorMessage.deleteCharAt(errorMessage.length() - 1);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                "message", errorMessage.toString(),
                "status", false
        ));
    }
}
