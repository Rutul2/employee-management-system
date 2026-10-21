package com.rutul.employee.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
@EnableMethodSecurity
public class SecurityConfig {
  @Bean PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }
  @Bean UserDetailsService users(PasswordEncoder encoder) {
    return new InMemoryUserDetailsManager(
      User.withUsername("admin").password(encoder.encode("Admin123!")).roles("ADMIN").build(),
      User.withUsername("staff").password(encoder.encode("Staff123!")).roles("STAFF").build());
  }
  @Bean SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http.csrf(csrf -> csrf.ignoringRequestMatchers("/api/**", "/h2-console/**"))
      .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
      .authorizeHttpRequests(auth -> auth.requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**", "/h2-console/**").permitAll().anyRequest().authenticated())
      .httpBasic(Customizer.withDefaults()).build();
  }
}
