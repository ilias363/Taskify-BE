package ma.ilias.taskifybe.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ma.ilias.taskifybe.dto.ITaskDtoDateValidation;

public class DeadlineAfterStartDateValidator implements ConstraintValidator<DeadlineAfterStartDate, ITaskDtoDateValidation> {
    @Override
    public boolean isValid(ITaskDtoDateValidation taskDto, ConstraintValidatorContext context) {
        if (taskDto.getStartDate() == null || taskDto.getDeadline() == null) {
            return true;
        }
        return taskDto.getDeadline().isAfter(taskDto.getStartDate());
    }
}
