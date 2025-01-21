package ma.ilias.taskifybe.mapper;

import ma.ilias.taskifybe.dao.entities.Task;
import ma.ilias.taskifybe.dto.TaskDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {
    private final ModelMapper modelMapper = new ModelMapper();

    public TaskDto fromTaskToTaskDto(Task task) {
        return modelMapper.map(task, TaskDto.class);
    }

    public Task fromTaskDtoToTask(TaskDto taskDto) {
        return modelMapper.map(taskDto, Task.class);
    }
}
