package ma.ilias.taskifybe.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import ma.ilias.taskifybe.dto.AppUserDto;
import ma.ilias.taskifybe.dto.LoginRequestDto;
import ma.ilias.taskifybe.dto.NewAppUserDto;
import ma.ilias.taskifybe.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    public ResponseEntity<AppUserDto> register(@RequestBody NewAppUserDto newAppUserDto) {
        AppUserDto registeredUser = appUserService.createAppUser(newAppUserDto);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDto loginRequestDto, HttpServletRequest request) {
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

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String authUserEmail = userDetails.getUsername();
            AppUserDto authUser = appUserService.getAppUserByEmail(authUserEmail);

            return ResponseEntity.ok(authUser);
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                    "message", "Invalid credentials",
                    "status", false
            ));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                    "message", "Authentication failed : " + ex.getMessage(),
                    "status", false
            ));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        SecurityContextHolder.clearContext();

        return ResponseEntity.ok(Map.of(
                "message", "Logout successful",
                "status", true
        ));
    }

    @GetMapping("/isloggedin")
    public ResponseEntity<Map<String, Boolean>> isLoggedIn(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        boolean isLoggedIn = session != null && session.getAttribute("SPRING_SECURITY_CONTEXT") != null;

        return ResponseEntity.ok(Map.of(
                "isLoggedIn", isLoggedIn
        ));
    }

    @GetMapping("/test")
    public ResponseEntity<?> testGet() {
        return ResponseEntity.ok(Map.of(
                "message", "successful",
                "status", true
        ));
    }

    @PostMapping("/test")
    public ResponseEntity<?> testPost(@RequestBody LoginRequestDto loginRequestDto) {

        return ResponseEntity.ok(Map.of(
                "your email", loginRequestDto.getPassword(),
                "your password", loginRequestDto.getPassword()
        ));
    }
}
