package com.charlyken.bibliotech.dataLoader;

import com.charlyken.bibliotech.model.AppUser;
import com.charlyken.bibliotech.repository.AppUserRepository;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
@RequiredArgsConstructor
@Profile("dev")
@Order(3)
public class UserDataLoader implements CommandLineRunner {
    private final AppUserRepository appUserRepository;
    private final Faker faker = new Faker(new Locale("fr"));
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run (String [] args){
        if(appUserRepository.count() == 0){
            List<AppUser> appUsers = new ArrayList<>();

            //L'admin
            AppUser admin = new AppUser();
            admin.setUsername("admin123");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole("ADMIN");
            appUserRepository.save(admin);

            for (int i = 0; i<10 ; i++){
                AppUser appUser = new AppUser();

                appUser.setUsername(faker.name().username());
                appUser.setPassword(passwordEncoder.encode(faker.name().username()));
                appUser.setRole("USER");

                appUsers.add(appUser);
            }
            appUserRepository.saveAll(appUsers);
        }
        System.out.println("Les Users sont initialisés");
    }
}
