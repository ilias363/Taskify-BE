package ma.ilias.taskifybe.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import ma.ilias.taskifybe.dto.AppUserDto;
import ma.ilias.taskifybe.dto.ResponseDto;
import ma.ilias.taskifybe.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
public class AppUserController {
    @Autowired
    private AppUserService appUserService;

    @GetMapping("/me")
    public ResponseEntity<ResponseDto<AppUserDto>> authenticatedUser(@AuthenticationPrincipal UserDetails userDetails) {

        String currentUserEmail = userDetails.getUsername();
        AppUserDto currentUser = appUserService.getAppUserByEmail(currentUserEmail);

        return ResponseEntity.ok(new ResponseDto<>("Successful", true, currentUser));
    }

    @DeleteMapping("/me")
    public ResponseEntity<ResponseDto<AppUserDto>> deleteAuthenticatedUser(
            HttpServletRequest request,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        String email = userDetails.getUsername();
        Boolean deleted = appUserService.deleteAppUserByEmail(email);

        if (deleted == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDto<>("User not found", false));

        if (deleted) {
            HttpSession session = request.getSession(false);
            if (session != null) session.invalidate();
            SecurityContextHolder.clearContext();
            return ResponseEntity.ok(new ResponseDto<>("User deleted successfully", true));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDto<>("Error deleting the user", false));
        }
    }
}
