package ma.ilias.taskifybe.service;

import ma.ilias.taskifybe.dto.AppUserDto;
import ma.ilias.taskifybe.dto.NewAppUserDto;

import java.util.List;

public interface AppUserService {
    AppUserDto createAppUser(NewAppUserDto newAppUserDto);
    List<AppUserDto> getAllAppUsers();
    AppUserDto getAppUserById(Long id);
    AppUserDto getAppUserByEmail(String email);
    Boolean deleteAppUser(Long id);
    AppUserDto updateAppUser(Long id, AppUserDto appUserDto);
}
