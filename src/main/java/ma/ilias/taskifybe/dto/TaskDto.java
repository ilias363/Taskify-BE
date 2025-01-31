package ma.ilias.taskifybe.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import ma.ilias.taskifybe.validation.DeadlineAfterStartDate;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@DeadlineAfterStartDate
public class TaskDto implements ITaskDtoDateValidation {
    @NotNull(message = "(be) Id is required")
    private Long id;

    @NotBlank(message = "(be) Title is required")
    @Size(max = 50, message = "(be) Title must be at most 50 characters long")
    private String title;

    @NotBlank(message = "(be) Description is required")
    @Size(max = 1000, message = "(be) Description must be at most 1000 characters long")
    private String description;

    @Pattern(
            regexp = "TODO|IN_PROGRESS|COMPLETED|CANCELLED",
            message = "(be) Status is not valid"
    )
    private String status;

    @NotNull(message = "(be) Start date is required")
    private LocalDateTime startDate;

    @NotNull(message = "(be) Deadline is required")
    private LocalDateTime deadline;

    @ToString.Exclude
    @JsonIgnoreProperties("tasks")
    private AppUserDto appUser;
}
