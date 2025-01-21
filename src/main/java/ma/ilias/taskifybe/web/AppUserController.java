package ma.ilias.taskifybe.web;

import ma.ilias.taskifybe.dto.AppUserDto;
import ma.ilias.taskifybe.dto.NewAppUserDto;
import ma.ilias.taskifybe.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class AppUserController {
    @Autowired
    private AppUserService appUserService;

    @GetMapping("/me")
    public ResponseEntity<AppUserDto> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String currentUserEmail = authentication.getName();
        AppUserDto currentUser = appUserService.getAppUserByEmail(currentUserEmail);

        return ResponseEntity.ok(currentUser);
    }

    @PostMapping("/create")
    public ResponseEntity<AppUserDto> createUser(@RequestBody NewAppUserDto newAppUserDto) {
        AppUserDto createdUser = appUserService.createAppUser(newAppUserDto);
        return ResponseEntity.ok(createdUser);
    }

    @GetMapping
    public ResponseEntity<List<AppUserDto>> getAllUsers() {
        return ResponseEntity.ok(appUserService.getAllAppUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppUserDto> getUserById(@PathVariable Long id) {
        AppUserDto userDto = appUserService.getAppUserById(id);
        return userDto != null ? ResponseEntity.ok(userDto) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppUserDto> updateUser(@PathVariable Long id, @RequestBody AppUserDto appUserDto) {
        AppUserDto updatedUser = appUserService.updateAppUser(id, appUserDto);
        return updatedUser != null ? ResponseEntity.ok(updatedUser) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        return appUserService.deleteAppUser(id) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
