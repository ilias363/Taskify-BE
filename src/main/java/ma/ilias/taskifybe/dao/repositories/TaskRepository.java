package ma.ilias.taskifybe.dao.repositories;

import ma.ilias.taskifybe.dao.entities.Task;
import ma.ilias.taskifybe.enums.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByAppUser_EmailOrderByCreatedAtDesc(String appUserEmail);
//    Page<Task> findByAppUser_EmailOrderByCreatedAtDesc(String appUserEmail, Pageable pageable);
//    Page<Task> findByAppUser_EmailAndStatusOrderByCreatedAtDesc(String appUserEmail, TaskStatus status, Pageable pageable);
//    Page<Task> findByAppUser_EmailAndTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrderByCreatedAtDesc(String appUserEmail, String title, String description, Pageable pageable);
//    Page<Task> findByAppUser_EmailAndStatusAndTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrderByCreatedAtDesc(String appUserEmail, TaskStatus status, String title, String description, Pageable pageable);
    @Query(
            """
            SELECT t FROM Task t
            WHERE t.appUser.email = :appUserEmail
                AND t.status = :status
                AND (LOWER(t.title) LIKE LOWER(CONCAT('%', :search, '%'))
                    OR LOWER(t.description) LIKE LOWER(CONCAT('%', :search, '%')))
            ORDER BY t.createdAt DESC
            """
    )
    Page<Task> findTasksByAppUserAndStatusAndSearchQuery(
            @Param("appUserEmail") String appUserEmail,
            @Param("status") TaskStatus status,
            @Param("search") String search,
            Pageable pageable
    );
    @Query(
            """
            SELECT t FROM Task t
            WHERE t.appUser.email = :appUserEmail
                AND (LOWER(t.title) LIKE LOWER(CONCAT('%', :search, '%'))
                    OR LOWER(t.description) LIKE LOWER(CONCAT('%', :search, '%')))
            ORDER BY t.createdAt DESC
            """
    )
    Page<Task> findTasksByAppUserAndSearchQuery(
            @Param("appUserEmail") String appUserEmail,
            @Param("search") String search,
            Pageable pageable
    );

}
