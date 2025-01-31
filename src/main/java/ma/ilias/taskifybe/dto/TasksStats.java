package ma.ilias.taskifybe.dto;

import lombok.*;

@AllArgsConstructor
@Data
@ToString
@Builder
public class TasksStats {
    private int all;
    private int todo;
    private int in_progress;
    private int completed;
    private int cancelled;
}
