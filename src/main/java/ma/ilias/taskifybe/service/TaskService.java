package ma.ilias.taskifybe.service;

import ma.ilias.taskifybe.dto.CustomPageable;
import ma.ilias.taskifybe.dto.NewTaskDto;
import ma.ilias.taskifybe.dto.TaskDto;

import java.util.List;

public interface TaskService {
    List<TaskDto> getUserTasks(String email);
    CustomPageable<TaskDto> getUserTasksPaginated(String email, String statusFilter, String searchQuery, int page, int size);
    TaskDto createTask(String email, NewTaskDto taskDto);
    TaskDto getTaskById(Long id);
    Boolean deleteTask(Long id);
    TaskDto updateTask(Long id, TaskDto taskDto);
}
