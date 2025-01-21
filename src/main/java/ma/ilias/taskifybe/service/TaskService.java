package ma.ilias.taskifybe.service;

import ma.ilias.taskifybe.dto.TaskDto;

import java.util.List;

public interface TaskService {
    TaskDto createTask(Long userId, TaskDto taskDto);
    List<TaskDto> getUserTasks(Long userId);
    TaskDto getTaskById(Long id);
    Boolean deleteTask(Long id);
    TaskDto updateTask(Long id, TaskDto taskDto);
}
