package ma.ilias.taskifybe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class LoginResponseDto {
    private String jwtToken;
    private String email;
    private List<String> roles;
}
