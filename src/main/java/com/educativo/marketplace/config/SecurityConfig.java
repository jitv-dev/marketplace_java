package com.educativo.marketplace.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        var vendedor = User.builder()
                .username("vendedor")
                .password(encoder.encode("vende123"))
                .roles("ADMIN", "USER")
                .build();

        var comprado  = User.builder()
                .username("comprado")
                .password(encoder.encode("comprado123"))
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(vendedor, comprado);
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // Explorar el catálogo es público
                        .requestMatchers("/", "/productos", "/login",
                                "/h2-console/**", "/css/**", "/js/**").permitAll()
                        // Gestión de productos solo ADMIN
                        .requestMatchers("/productos/nuevo", "/productos/guardar",
                                "/productos/editar/**",
                                "/productos/eliminar/**").hasRole("ADMIN")
                        // Comprar requiere login
                        .requestMatchers("/ordenes/**").authenticated()
                        // Todo lo demás requiere autenticación
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/?logout=true")
                        .invalidateHttpSession(true)
                        .permitAll()
                )
                .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
                .headers(h -> h.frameOptions(f -> f.sameOrigin()));

        return http.build();
    }

}
