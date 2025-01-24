package ma.ilias.taskifybe.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequestDto {
    @NotBlank(message = "(be) Email is required")
    @Email(message = "(be) Invalid email format")
    private String email;

    @NotBlank(message = "(be) Password is required")
    @Size(min = 6, message = "(be) Password must be at least 6 characters long")
    private String password;
}
