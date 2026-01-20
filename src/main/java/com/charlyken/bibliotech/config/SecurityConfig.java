package com.charlyken.bibliotech.config;

import com.charlyken.bibliotech.service.CustomUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;

/**
 * C'est la classe de configuration de la securité
 */
@Configuration
@EnableWebSecurity // active Spring security
@AllArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    //Methode de Hash du mdp
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //La chaine de filtre de securité
    @Bean
    public DefaultSecurityFilterChain customSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                // Désactivation du CSRF
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((requests) -> requests
                        // Tout le monde peut voir la page d'accueil et le login
                        .requestMatchers("/", "/home", "/register", "/user", "/css/**", "/js/**").permitAll()
                        // Seuls les ADMINS peuvent accéder aux routes commençant par /admin
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        // Toute autre requête nécessite d'être connecté
                        .anyRequest().authenticated()
                )

                // On active le formulaire de login par défaut de Spring
                .formLogin((form) -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/",true)
                        .failureUrl("/login?error=true")
                        .permitAll())
                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout=true")
                        .permitAll())

                //On fait maintenant lien avec le user en Bd
                .userDetailsService(customUserDetailsService);

        return http.build();
    }
}
