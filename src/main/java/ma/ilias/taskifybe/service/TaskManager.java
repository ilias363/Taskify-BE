package ma.ilias.taskifybe.service;

import ma.ilias.taskifybe.dao.entities.AppUser;
import ma.ilias.taskifybe.dao.entities.Task;
import ma.ilias.taskifybe.dao.repositories.AppUserRepository;
import ma.ilias.taskifybe.dao.repositories.TaskRepository;
import ma.ilias.taskifybe.dto.TaskDto;
import ma.ilias.taskifybe.enums.TaskStatus;
import ma.ilias.taskifybe.mapper.AppUserMapper;
import ma.ilias.taskifybe.mapper.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private AppUserMapper appUserMapper;

    @Override
    public TaskDto createTask(Long userId, TaskDto taskDto) {
        Task task = taskMapper.fromTaskDtoToTask(taskDto);
        AppUser user = appUserRepository.findById(userId).orElse(null);

        if (user == null) return null;
        task.setAppUser(user);

        return taskMapper.fromTaskToTaskDto(taskRepository.save(task));
    }

    @Override
    public List<TaskDto> getUserTasks(Long userId) {
        return taskRepository.findByAppUser_Id(userId).stream().map(taskMapper::fromTaskToTaskDto).toList();
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
        existingTask.setDeadline(taskDto.getDeadline());

        return taskMapper.fromTaskToTaskDto(taskRepository.save(existingTask));
    }
}
