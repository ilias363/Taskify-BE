package ma.ilias.taskifybe.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ma.ilias.taskifybe.dao.entities.AppUser;
import ma.ilias.taskifybe.dto.AppUserDto;
import ma.ilias.taskifybe.dto.LoginRequestDto;
import ma.ilias.taskifybe.dto.LoginResponseDto;
import ma.ilias.taskifybe.dto.NewAppUserDto;
import ma.ilias.taskifybe.jwt.JwtUtils;
import ma.ilias.taskifybe.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AppUserService appUserService;

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppUserDto> register(@RequestBody NewAppUserDto newAppUserDto) {
        AppUserDto registeredUser = appUserService.createAppUser(newAppUserDto);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createJwtToken(@RequestBody LoginRequestDto loginRequestDto) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginRequestDto.getEmail(), loginRequestDto.getPassword()
            ));
        } catch (DisabledException e) {
            Map<String, Object> errorMap = new HashMap<>();
            errorMap.put("message", "Account is disabled");
            errorMap.put("status", false);
            return new ResponseEntity<Object>(errorMap, HttpStatus.LOCKED);
        } catch (BadCredentialsException e) {
            Map<String, Object> errorMap = new HashMap<>();
            errorMap.put("message", "Invalid credentials");
            errorMap.put("status", false);
            return new ResponseEntity<Object>(errorMap, HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            Map<String, Object> errorMap = new HashMap<>();
            errorMap.put("message", "Authentication failed: " + e.getMessage());
            errorMap.put("status", false);
            return new ResponseEntity<Object>(errorMap, HttpStatus.UNAUTHORIZED);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        final String jwtToken = jwtUtils.generateToken(userDetails);
        final List<String> roles = authentication.getAuthorities()
                .stream()
                .map(role -> role.getAuthority())
                .toList();

        return ResponseEntity.ok(new LoginResponseDto(jwtToken, userDetails.getUsername(), roles));
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextHolder.clearContext();
        request.getSession().invalidate();
        return ResponseEntity.ok("Logout successful");
    }
}
