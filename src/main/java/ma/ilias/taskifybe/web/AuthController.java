package ma.ilias.taskifybe.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import ma.ilias.taskifybe.dto.AppUserDto;
import ma.ilias.taskifybe.dto.LoginRequestDto;
import ma.ilias.taskifybe.dto.NewAppUserDto;
import ma.ilias.taskifybe.dto.ResponseDto;
import ma.ilias.taskifybe.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AppUserService appUserService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDto<Void>> register(@Valid @RequestBody NewAppUserDto newAppUserDto) {
        AppUserDto newUser = appUserService.createAppUser(newAppUserDto);
        return newUser != null ?
                ResponseEntity.ok(new ResponseDto<>("Register successful", true))
                :
                ResponseEntity.ok(new ResponseDto<>("Error creating the account", true));
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDto<Void>> login(@Valid @RequestBody LoginRequestDto loginRequestDto,
                                             HttpServletRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequestDto.getEmail(),
                            loginRequestDto.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            HttpSession newSession = request.getSession(true);
            newSession.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

            return ResponseEntity.ok(new ResponseDto<>("Login successful", true));
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new ResponseDto<>("Invalid credentials", false)
            );
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new ResponseDto<>("Authentication failed : " + ex.getMessage(), false)
            );
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<ResponseDto<Void>> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        SecurityContextHolder.clearContext();

        return ResponseEntity.ok(new ResponseDto<>("Logout successful", true));
    }

    @GetMapping("/isloggedin")
    public ResponseEntity<ResponseDto<Map<String, Boolean>>> isLoggedIn(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        boolean isLoggedIn = session != null && session.getAttribute("SPRING_SECURITY_CONTEXT") != null;

        return ResponseEntity.ok(
                new ResponseDto<>("Successful", true, Map.of("isLoggedIn", isLoggedIn))
        );
    }
}
