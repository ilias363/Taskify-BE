package ma.ilias.taskifybe.web;

import jakarta.validation.Valid;
import ma.ilias.taskifybe.dto.*;
import ma.ilias.taskifybe.enums.TaskStatus;
import ma.ilias.taskifybe.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

//    @GetMapping
//    public ResponseEntity<ResponseDto<List<TaskDto>>> getUserTasks(@AuthenticationPrincipal UserDetails userDetails) {
//        String email = userDetails.getUsername();
//        List<TaskDto> tasks = taskService.getUserTasks(email);
//        return ResponseEntity.ok( new ResponseDto<>("Successful", true, tasks));
//    }

    @GetMapping
    public ResponseEntity<ResponseDto<?>> getPaginatedUserTasks(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(name = "status", defaultValue = "all" ) String statusFilter,
            @RequestParam(name = "search", defaultValue = "" ) String searchQuery,
            @RequestParam(name = "page", defaultValue = "0" ) int page,
            @RequestParam(name = "size", defaultValue = "9" ) int size
    ) {
        if (
                !statusFilter.equalsIgnoreCase("all") &&
                Arrays.stream(TaskStatus.values()).noneMatch(e -> e.name().equalsIgnoreCase(statusFilter))
        ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseDto<>("Status filter not found", false)
            );
        }

        String email = userDetails.getUsername();
        CustomPageable<TaskDto> tasksPaginated = taskService.getUserTasksPaginated(
                email,
                statusFilter.equalsIgnoreCase("all") ? null : statusFilter.toUpperCase(),
                searchQuery,
                page,
                size
        );

        return ResponseEntity.ok( new ResponseDto<>("Successful", true, tasksPaginated));
    }

    @GetMapping("stats")
    public ResponseEntity<ResponseDto<TasksStats>> getUserTasksStats(@AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        List<TaskDto> tasks = taskService.getUserTasks(email);
        return ResponseEntity.ok(
                new ResponseDto<>("Successful", true,
                        TasksStats
                                .builder()
                                .all(tasks.size())
                                .todo((int) tasks.stream().filter(task -> task.getStatus() == TaskStatus.TODO.toString()).count())
                                .in_progress((int) tasks.stream().filter(task -> task.getStatus() == TaskStatus.IN_PROGRESS.toString()).count())
                                .completed((int) tasks.stream().filter(task -> task.getStatus() == TaskStatus.COMPLETED.toString()).count())
                                .cancelled((int) tasks.stream().filter(task -> task.getStatus() == TaskStatus.CANCELLED.toString()).count())
                                .build()
                )
        );
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseDto<Void>> createTask(@AuthenticationPrincipal UserDetails userDetails,
                                                        @Valid @RequestBody NewTaskDto newTaskDto) {
        String email = userDetails.getUsername();
        TaskDto createdTask = taskService.createTask(email, newTaskDto);
        return createdTask != null ?
                ResponseEntity.ok(
                        new ResponseDto<>("Task created successfully", true)
                )
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseDto<>("Task creation failed, user not found", false)
                );
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<ResponseDto<TaskDto>> getTaskById(@PathVariable Long taskId) {
        TaskDto taskDto = taskService.getTaskById(taskId);
        return taskDto != null ?
                ResponseEntity.ok(
                        new ResponseDto<>("Successful", true, taskDto)
                )
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseDto<>("Task not found", false)
                );
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<ResponseDto<Void>> deleteTask(@PathVariable Long taskId) {
        return taskService.deleteTask(taskId) ?
                ResponseEntity.ok(
                        new ResponseDto<>("Task deleted successfully", true)
                )
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseDto<>("Task deletion failed", false)
                );
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<ResponseDto<TaskDto>> updateTask(@PathVariable Long taskId,
                                                           @Valid @RequestBody TaskDto taskDto) {
        TaskDto updatedTask = taskService.updateTask(taskId, taskDto);
        return updatedTask != null ?
                ResponseEntity.ok(
                        new ResponseDto<>("Task updated successfully", true, updatedTask)
                )
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseDto<>("Task not found", false)
                );
    }

}
