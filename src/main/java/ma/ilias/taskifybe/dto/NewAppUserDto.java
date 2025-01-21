package ma.ilias.taskifybe.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class NewAppUserDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
