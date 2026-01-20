package com.charlyken.bibliotech.service;

import com.charlyken.bibliotech.model.AppUser;
import com.charlyken.bibliotech.repository.AppUserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * C'est le pont entre les donneés "users" de la BD et Spring Security
 * Spring Security ne comprend que les objets de type UserDetails
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AppUserRepository repository;

    public CustomUserDetailsService(AppUserRepository repository){
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        AppUser appUser = repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé : "+username));

        return new User(
                appUser.getUsername(),
                appUser.getPassword(),
                mapRolesToAuthorities(appUser.getRole())
        );
    }

    public Collection<? extends GrantedAuthority> mapRolesToAuthorities(String role) {
        // Transforme ta String "ADMIN" en autorité compréhensible par Spring
        String roleWithPrefix = role.startsWith("ROLE_") ? role : "ROLE_" + role;
        return List.of(new SimpleGrantedAuthority(roleWithPrefix));
    }
}
