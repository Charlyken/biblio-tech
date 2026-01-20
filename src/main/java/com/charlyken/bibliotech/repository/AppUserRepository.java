package com.charlyken.bibliotech.repository;

import com.charlyken.bibliotech.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByUsername(String username);

    @Query("SELECT u.id FROM AppUser u WHERE u.username = :username")
    Long findIdByUsername(String username);
}
