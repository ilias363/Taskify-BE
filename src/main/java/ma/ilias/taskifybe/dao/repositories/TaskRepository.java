package ma.ilias.taskifybe.dao.repositories;

import ma.ilias.taskifybe.dao.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByAppUser_Id(Long id);
}
