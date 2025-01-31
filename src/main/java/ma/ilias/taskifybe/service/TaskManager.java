package ma.ilias.taskifybe.service;

import ma.ilias.taskifybe.dao.entities.AppUser;
import ma.ilias.taskifybe.dao.entities.Task;
import ma.ilias.taskifybe.dao.repositories.AppUserRepository;
import ma.ilias.taskifybe.dao.repositories.TaskRepository;
import ma.ilias.taskifybe.dto.CustomPageable;
import ma.ilias.taskifybe.dto.NewTaskDto;
import ma.ilias.taskifybe.dto.TaskDto;
import ma.ilias.taskifybe.enums.TaskStatus;
import ma.ilias.taskifybe.mapper.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskManager implements TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private AppUserRepository appUserRepository;

    @Override
    public List<TaskDto> getUserTasks(String email) {
        return taskRepository.findByAppUser_EmailOrderByCreatedAtDesc(email).stream()
                .map(taskMapper::fromTaskToTaskDto).toList();
    }

    @Override
    public CustomPageable<TaskDto> getUserTasksPaginated(String email, String statusFilter, String searchQuery, int page, int size) {
        Page<Task> tasks = statusFilter != null ?
                taskRepository.findTasksByAppUserAndStatusAndSearchQuery(
                        email, TaskStatus.valueOf(statusFilter), searchQuery, PageRequest.of(page, size)
                )
                : taskRepository.findTasksByAppUserAndSearchQuery(
                        email, searchQuery, PageRequest.of(page, size)
                );

        return new CustomPageable<>(
                page, size, tasks.getTotalElements(), tasks.getTotalPages(),
                tasks.getContent().stream().map(taskMapper::fromTaskToTaskDto).toList()
        );
    }

    @Override
    public TaskDto createTask(String email, NewTaskDto newTaskDto) {
        Task task = taskMapper.fromNewTaskDtoToTask(newTaskDto);
        AppUser user = appUserRepository.findByEmail(email).orElse(null);

        if (user == null) return null;
        task.setAppUser(user);

        return taskMapper.fromTaskToTaskDto(taskRepository.save(task));
    }

    @Override
    public TaskDto getTaskById(Long id) {
        Task task = taskRepository.findById(id).orElse(null);
        return task != null ? taskMapper.fromTaskToTaskDto(task) : null;
    }

    @Override
    public Boolean deleteTask(Long id) {
        taskRepository.deleteById(id);
        return !taskRepository.existsById(id);
    }

    @Override
    public TaskDto updateTask(Long id, TaskDto taskDto) {
        Task existingTask = taskRepository.findById(id).orElse(null);
        if (existingTask == null) return null;

        existingTask.setTitle(taskDto.getTitle());
        existingTask.setDescription(taskDto.getDescription());
        existingTask.setStatus(TaskStatus.valueOf(taskDto.getStatus()));
        existingTask.setStartDate(taskDto.getStartDate());
        existingTask.setDeadline(taskDto.getDeadline());

        return taskMapper.fromTaskToTaskDto(taskRepository.save(existingTask));
    }
}
