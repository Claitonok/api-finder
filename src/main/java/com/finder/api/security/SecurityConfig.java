package com.finder.api.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.core.userdetails.User;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.finder.api.exception.MyRuntimeException;
import com.finder.api.service.MyUserDetailsService;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final MyUserDetailsService myUserDetailsService;

    SecurityConfig(MyUserDetailsService myUserDetailsService) {
        this.myUserDetailsService = myUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws MyRuntimeException {

        http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(auth -> auth
                // 1. Libera o endpoint de criação de usuário de segurança
                // Após criar seu usuário, você pode mudar para .authenticated() ou remover esta linha

                // Opção De:
                .requestMatchers("/api/auth/config/**").permitAll()
                // Para:
                // .requestMatchers("/api/auth/config/**").hasRole("ADMIN")
                // ---------------------------------------------------------//
                .anyRequest().authenticated())

                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults())
                .cors(Customizer.withDefaults())
                .userDetailsService(myUserDetailsService)

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout") // Redireciona para o login após sair
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID"))

                // Limita a sessão a um único login por usuário
                .sessionManagement(session -> session
                        // Impede que o usuário faça login em outro dispositivo se já estiver logado
                        .maximumSessions(2) // Permite no máximo 2 sessões por usuário
                        // Se quiser permitir que o usuário faça login em outro dispositivo,
                        // mas expire a sessão anterior, use .maxSessionsPreventsLogin(false)
                        .maxSessionsPreventsLogin(false));

        return http.build();
    }

    // @Bean
    // public UserDetailsService userDetailsService() {
    // UserDetails user = User.builder()
    // .username("admin")
    // .password(passwordEncoder().encode("123"))
    // .roles("USER")
    // .build();

    // return new InMemoryUserDetailsManager(user);
    // }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}