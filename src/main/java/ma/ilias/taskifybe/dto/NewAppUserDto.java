package ma.ilias.taskifybe.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import ma.ilias.taskifybe.validation.UniqueEmail;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class NewAppUserDto {
    @NotBlank(message = "(be) First name is required")
    private String firstName;

    @NotBlank(message = "(be) Last name is required")
    private String lastName;

    @NotBlank(message = "(be) Email is required")
    @Email(message = "(be) Invalid email format")
    @UniqueEmail
    private String email;

    @NotBlank(message = "(be) Password is required")
    @Size(min = 6, message = "(be) Password must be at least 6 characters long")
    private String password;
}
