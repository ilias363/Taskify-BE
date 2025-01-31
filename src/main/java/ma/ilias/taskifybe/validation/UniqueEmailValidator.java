package ma.ilias.taskifybe.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ma.ilias.taskifybe.dao.repositories.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    @Autowired
    private AppUserRepository appUserRepository;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return email != null && !appUserRepository.existsByEmail(email);
    }
}
