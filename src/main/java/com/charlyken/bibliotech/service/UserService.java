package com.charlyken.bibliotech.service;

import java.util.Optional;

import com.charlyken.bibliotech.exception.UserRuleException;
import com.charlyken.bibliotech.mapper.UserMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import com.charlyken.bibliotech.dto.UserDto;
import org.springframework.stereotype.Service;
import com.charlyken.bibliotech.model.AppUser;
import com.charlyken.bibliotech.repository.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;



@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final AppUserRepository appUserRepository;
    private final UserMapper userMapper;
    private static final String USER_ROLE = "USER";

    @Transactional
    public void registrationUser(UserDto userDto){
        if(appUserRepository.findByUsername(userDto.getUsername()).isPresent()){
            throw new UserRuleException("Le username " +userDto.getUsername() + " existe dèjà");
        }

        AppUser appUser = userMapper.mapToEntity(userDto);
        appUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        appUser.setRole(USER_ROLE);

        appUserRepository.save(appUser);
    }

    public Long findIdByUsername(String username){
        return appUserRepository.findIdByUsername(username);
    }

    public Optional<AppUser> findByUsername(String username){
        Optional<AppUser> appUser = appUserRepository.findByUsername(username);

        return appUser;
    }

    public long totalUsers (){
        return appUserRepository.count();
    }




}
