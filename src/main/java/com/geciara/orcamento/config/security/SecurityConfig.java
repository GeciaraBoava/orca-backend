package com.geciara.orcamento.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final SecurityFilter securityFilter;

    public SecurityConfig(CustomUserDetailsService userDetailsService, SecurityFilter securityFilter) {
        this.userDetailsService = userDetailsService;
        this.securityFilter = securityFilter;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(BCryptPasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, DaoAuthenticationProvider daoAuthProvider) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
            .authenticationProvider(daoAuthProvider)
            .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                    // Público
                    .requestMatchers("/login", "/register", "/auth/login", "/auth/register", "/img/**", "/css/**", "/js/**")
                    .permitAll()
                    .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html")
                    .permitAll()

                    // Apenas ADMIN
                    .requestMatchers("/config/**", "/admin/**")
                    .hasAnyAuthority("ROLE_ADMIN")

                    // Apenas MANAGER e ADMIN
                    .requestMatchers("/api/users/**", "/api/users")
                    .hasAnyAuthority("ROLE_MANAGER", "ROLE_ADMIN")

                    // BUDGET, MANAGER e ADMIN → CRUD de orçamentos, produtos, insumos
                    .requestMatchers("/budgets/**", "/products/**", "/items/**", "/materials/**")
                    .hasAnyAuthority("ROLE_BUDGET", "ROLE_MANAGER", "ROLE_ADMIN")

                    // COMMERCIAL pode visualizar orçamentos (ex: páginas de consulta/impressão)
                    .requestMatchers(HttpMethod.GET, "/budgets/**", "/products/**")
                    .hasAnyAuthority("ROLE_COMMERCIAL", "ROLE_BUDGET", "ROLE_MANAGER", "ROLE_ADMIN")

                    // Demais requisições → autenticadas
                    .anyRequest().authenticated()
            )
            .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
