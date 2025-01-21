package ma.ilias.taskifybe.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class TaskDto {
    private Long id;
    private String title;
    private String description;
    private String status;
    private LocalDateTime deadline;
    @ToString.Exclude
    @JsonIgnoreProperties("tasks")
    private AppUserDto appUser;
}
