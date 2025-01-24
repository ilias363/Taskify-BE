package ma.ilias.taskifybe;

import ma.ilias.taskifybe.dao.entities.AppUser;
import ma.ilias.taskifybe.dao.entities.Task;
import ma.ilias.taskifybe.dao.repositories.TaskRepository;
import ma.ilias.taskifybe.dao.repositories.AppUserRepository;
import ma.ilias.taskifybe.enums.TaskStatus;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
//@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class TaskifyBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskifyBeApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(
//            AppUserRepository appUserRepository,
//            TaskRepository taskRepository,
//            BCryptPasswordEncoder passwordEncoder
    ) {
        return args -> {
//            AppUser appUser1 = AppUser.builder().firstName("John").lastName("Doe")
//                    .email("johndoe@example.com").password(passwordEncoder.encode("password123")).build();
//
//            AppUser appUser2 = AppUser.builder().firstName("Jane").lastName("Smith")
//                    .email("janesmith@example.com").password(passwordEncoder.encode("password456")).build();
//
//            AppUser appUser3 = AppUser.builder().firstName("Ilias").lastName("Ouaddaf")
//                    .email("ilias@gmail.com").password(passwordEncoder.encode("rootroot")).build();
//
//            Task task1 = Task.builder().title("Finish project report")
//                    .description("Complete the project report by next week")
//                    .status(TaskStatus.IN_PROGRESS).deadline(LocalDateTime.now().plusDays(7))
//                    .appUser(appUser1).build();
//
//            Task task2 = Task.builder().title("Prepare presentation")
//                    .description("Prepare slides for the team meeting")
//                    .status(TaskStatus.IN_PROGRESS).deadline(LocalDateTime.now().plusDays(3))
//                    .appUser(appUser2).build();
//
//            Task task3 = Task.builder().title("Update website content")
//                    .description("Revise the homepage and add new product descriptions")
//                    .status(TaskStatus.COMPLETED).deadline(LocalDateTime.now().minusDays(2))
//                    .appUser(appUser1).build();
//
//            Task task4 = Task.builder().title("Schedule team meeting")
//                    .description("Organize a meeting for project planning")
//                    .status(TaskStatus.CANCELLED).deadline(LocalDateTime.now().plusDays(1))
//                    .appUser(appUser2).build();
//
//            Task task5 = Task.builder().title("Schedule team meeting 2")
//                    .description("Organize a meeting for project planning 2")
//                    .status(TaskStatus.CANCELLED).deadline(LocalDateTime.now().plusDays(1))
//                    .appUser(appUser3).build();
//
//            Task task6 = Task.builder().title("Schedule team meeting 3")
//                    .description("Organize a meeting for project planning 3")
//                    .status(TaskStatus.CANCELLED).deadline(LocalDateTime.now().plusDays(1))
//                    .appUser(appUser3).build();
//
//            appUserRepository.saveAll(List.of(appUser1, appUser2, appUser3));
//            taskRepository.saveAll(List.of(task1, task2, task3, task4, task5, task6));
        };
    }
}
