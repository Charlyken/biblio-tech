package com.charlyken.bibliotech.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDto {
    @NotBlank(message = "Le username est obligatoire")
    @Size(min = 5, max = 15, message = "Le username est entre 5 et 15 caractères")
    private String username;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!*]).{8,20}$",
            message = "Le mot de passe doit contenir entre 8 et 20 caractères, " +
                    "avec au moins une majuscule, une minuscule, un chiffre et un caractère spécial (@#$%^&+=!*)")
    private String password;

    private String role;

    private boolean active = true;
}
