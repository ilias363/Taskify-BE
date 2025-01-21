package ma.ilias.taskifybe.mapper;

import ma.ilias.taskifybe.dao.entities.AppUser;
import ma.ilias.taskifybe.dto.NewAppUserDto;
import ma.ilias.taskifybe.dto.AppUserDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AppUserMapper {
    private final ModelMapper modelMapper = new ModelMapper();

    public AppUserDto fromUserToUserDto(AppUser appUser) {
        return modelMapper.map(appUser, AppUserDto.class);
    }

    public AppUser fromUserDtoToUser(AppUserDto appUserDto) {
        return modelMapper.map(appUserDto, AppUser.class);
    }

    public AppUser fromNewUserDtoToUser(NewAppUserDto newAppUserDto) {
        return modelMapper.map(newAppUserDto, AppUser.class);
    }
}
