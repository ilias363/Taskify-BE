package ma.ilias.taskifybe.service;

import ma.ilias.taskifybe.dao.entities.AppUser;
import ma.ilias.taskifybe.dao.repositories.AppUserRepository;
import ma.ilias.taskifybe.dto.AppUserDto;
import ma.ilias.taskifybe.dto.NewAppUserDto;
import ma.ilias.taskifybe.mapper.AppUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUserManager implements AppUserService {
    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private AppUserMapper appUserMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AppUserDto createAppUser(NewAppUserDto newAppUserDto) {
        try {
            newAppUserDto.setPassword(passwordEncoder.encode(newAppUserDto.getPassword()));
            return appUserMapper.fromUserToUserDto(
                    appUserRepository.save(appUserMapper.fromNewUserDtoToUser(newAppUserDto))
            );
        } catch (Exception e) {
            return null;
        }
    }

    public List<AppUserDto> getAllAppUsers() {
        return appUserRepository.findAll().stream().map(appUserMapper::fromUserToUserDto).toList();
    }

    public AppUserDto getAppUserById(Long id) {
        AppUser appUser = appUserRepository.findById(id).orElse(null);
        return appUser == null ? null : appUserMapper.fromUserToUserDto(appUser);
    }

    @Override
    public AppUserDto getAppUserByEmail(String email) {
        AppUser appUser = appUserRepository.findByEmail(email).orElse(null);
        return appUser == null ? null : appUserMapper.fromUserToUserDto(appUser);
    }

    public AppUserDto updateAppUser(Long id, AppUserDto appUserDto) {
        AppUser existingUser = appUserRepository.findById(id).orElse(null);
        if (existingUser == null) return null;

        existingUser.setFirstName(appUserDto.getFirstName());
        existingUser.setLastName(appUserDto.getLastName());
        existingUser.setEmail(appUserDto.getEmail());

        return appUserMapper.fromUserToUserDto(appUserRepository.save(existingUser));
    }

    public Boolean deleteAppUserByEmail(String email) {
        AppUser appUser = appUserRepository.findByEmail(email).orElse(null);
        if (appUser == null) return null;
        appUserRepository.deleteById(appUser.getId());
        return !appUserRepository.existsById(appUser.getId());
    }
}
