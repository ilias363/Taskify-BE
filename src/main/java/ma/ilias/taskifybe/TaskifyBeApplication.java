package ma.ilias.taskifybe;

import ma.ilias.taskifybe.dao.entities.AppUser;
import ma.ilias.taskifybe.dao.entities.Task;
import ma.ilias.taskifybe.dao.repositories.TaskRepository;
import ma.ilias.taskifybe.dao.repositories.AppUserRepository;
import ma.ilias.taskifybe.enums.TaskStatus;
import ma.ilias.taskifybe.service.AppUserService;
import ma.ilias.taskifybe.service.TaskManager;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootApplication
//@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class TaskifyBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskifyBeApplication.class, args);
    }

//    @Bean
//    public CommandLineRunner commandLineRunner(AppUserService appUserService) {
//        return args -> {
//            System.out.println(appUserService.deleteAppUserByEmail("just@some.email"));
//        };
//    }

//    @Bean
//    public CommandLineRunner commandLineRunner(
//            AppUserRepository appUserRepository,
//            TaskRepository taskRepository,
//            BCryptPasswordEncoder passwordEncoder
//    ) {
//        return args -> {
//            AppUser appUser1 = AppUser.builder()
//                    .firstName("Ilias")
//                    .lastName("Ouaddaf")
//                    .email("ilias@gmail.com")
//                    .password(passwordEncoder.encode("rootroot"))
//                    .build();
//
//            Task task1 = Task.builder()
//                    .title("Complete Spring Boot project")
//                    .description("Finalize the Spring Boot application before the deadline")
//                    .status(TaskStatus.IN_PROGRESS)
//                    .startDate(LocalDateTime.now())
//                    .deadline(LocalDateTime.now().plusDays(5))
//                    .appUser(appUser1)
//                    .build();
//
//            Task task2 = Task.builder()
//                    .title("Team meeting preparation")
//                    .description("Prepare agenda and documents for the team meeting")
//                    .status(TaskStatus.TODO)
//                    .startDate(LocalDateTime.now())
//                    .deadline(LocalDateTime.now().plusDays(6))
//                    .appUser(appUser1)
//                    .build();
//
//            Task task3 = Task.builder()
//                    .title("Write blog post")
//                    .description("Write and publish a blog post about Java Streams")
//                    .status(TaskStatus.COMPLETED)
//                    .startDate(LocalDateTime.now().minusDays(7))
//                    .deadline(LocalDateTime.now().minusDays(2))
//                    .appUser(appUser1)
//                    .build();
//
//            Task task4 = Task.builder()
//                    .title("Prepare presentation")
//                    .description("Prepare slides for the team meeting")
//                    .status(TaskStatus.CANCELLED)
//                    .startDate(LocalDateTime.now())
//                    .deadline(LocalDateTime.now().plusDays(3))
//                    .appUser(appUser1).build();
//
//            Task task5 = Task.builder().title("Update website content")
//                    .description("Revise the homepage and add new product descriptions")
//                    .status(TaskStatus.COMPLETED)
//                    .startDate(LocalDateTime.now().minusDays(8))
//                    .deadline(LocalDateTime.now().minusDays(2))
//                    .appUser(appUser1).build();
//
//            appUserRepository.save(appUser1);
//            taskRepository.saveAll(List.of(task1, task2, task3, task4, task5));
//
//            for (int i = 1; i <= 3; i++) {
//                AppUser appUser = AppUser.builder()
//                        .firstName("User" + i)
//                        .lastName("LastName" + i)
//                        .email("user" + i + "@example.com")
//                        .password(passwordEncoder.encode("password" + i))
//                        .build();
//
//                appUserRepository.save(appUser);
//
//                for (int j = 1; j <= 20; j++) {
//                    Task task = Task.builder()
//                            .title("Task " + j + " for User" + i)
//                            .description("Description of task " + j + " for User" + i)
//                            .status(TaskStatus.values()[ThreadLocalRandom.current().nextInt(TaskStatus.values().length)])
//                            .startDate(LocalDateTime.now().minusDays(ThreadLocalRandom.current().nextInt(10)))
//                            .deadline(LocalDateTime.now().plusDays(ThreadLocalRandom.current().nextInt(10)))
//                            .appUser(appUser)
//                            .build();
//
//                    taskRepository.save(task);
//                }
//            }
//        };
//    }
}
