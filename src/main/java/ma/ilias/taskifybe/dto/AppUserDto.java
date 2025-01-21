package ma.ilias.taskifybe.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class AppUserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    @ToString.Exclude
    @JsonIgnoreProperties("appUser")
    private Collection<TaskDto> tasks;
}
