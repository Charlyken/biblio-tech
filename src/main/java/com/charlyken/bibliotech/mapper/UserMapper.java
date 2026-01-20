package com.charlyken.bibliotech.mapper;

import com.charlyken.bibliotech.dto.UserDto;
import com.charlyken.bibliotech.model.AppUser;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Component
public class UserMapper {
    public UserDto mapToDto(AppUser appUser) {
        if (appUser == null) {
            return null;
        }
        UserDto userDto = new UserDto();
        userDto.setUsername(appUser.getUsername());
        userDto.setPassword(appUser.getPassword());
        userDto.setActive(appUser.isActive());
        userDto.setRole(appUser.getRole());

        return userDto;
    }

    public AppUser mapToEntity(UserDto userDto) {
        if (userDto == null) {
            return null;
        }
        AppUser appUser = new AppUser();
        appUser.setUsername(userDto.getUsername());
        appUser.setPassword(userDto.getPassword());
        appUser.setRole(userDto.getRole());

        return appUser;
    }
}
