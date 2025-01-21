package ma.ilias.taskifybe.web;

import ma.ilias.taskifybe.dto.TaskDto;
import ma.ilias.taskifybe.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @PostMapping("/create")
    public ResponseEntity<TaskDto> createTask(@PathVariable Long userId, @RequestBody TaskDto taskDto) {
        TaskDto createdTask = taskService.createTask(userId, taskDto);
        return createdTask != null ? ResponseEntity.ok(createdTask) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<TaskDto>> getUserTasks(@PathVariable Long userId) {
        return ResponseEntity.ok(taskService.getUserTasks(userId));
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable Long taskId) {
        TaskDto taskDto = taskService.getTaskById(taskId);
        return taskDto != null ? ResponseEntity.ok(taskDto) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        return taskService.deleteTask(taskId) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable Long taskId, @RequestBody TaskDto taskDto) {
        TaskDto updatedTask = taskService.updateTask(taskId, taskDto);
        return  updatedTask != null ? ResponseEntity.ok(updatedTask) : ResponseEntity.notFound().build();
    }
}
