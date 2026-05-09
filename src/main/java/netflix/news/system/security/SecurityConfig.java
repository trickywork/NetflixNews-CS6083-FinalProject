package netflix.news.system.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

/**
 * Spring Security Configuration
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // Disable CSRF for REST API
            .csrf().disable()
            
            // Enable CORS
            .cors().configurationSource(corsConfigurationSource())
            
            .and()
            
            // Set session management to stateless
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            
            .and()
            
            // Set permissions on endpoints
            .authorizeRequests()
            
            // Public endpoints - authentication
            .antMatchers("/api/auth/**").permitAll()
            
            // Public endpoints - static resources
            .antMatchers("/", "/index.html", "/login.html", "/register.html").permitAll()
            .antMatchers("/css/**", "/js/**", "/images/**", "/assets/**").permitAll()
            .antMatchers("/*.html", "/*.css", "/*.js", "/*.ico").permitAll()
            
            // Public GET endpoints for viewing data
            .antMatchers(HttpMethod.GET, "/api/web-series/**").permitAll()
            .antMatchers(HttpMethod.GET, "/api/countries/**").permitAll()
            .antMatchers(HttpMethod.GET, "/api/schedules/**").permitAll()
            .antMatchers(HttpMethod.GET, "/api/statistics/**").permitAll()
            
            // Employee only endpoints
            .antMatchers(HttpMethod.POST, "/api/web-series/**").hasRole("EMPLOYEE")
            .antMatchers(HttpMethod.PUT, "/api/web-series/**").hasRole("EMPLOYEE")
            .antMatchers(HttpMethod.DELETE, "/api/web-series/**").hasRole("EMPLOYEE")
            .antMatchers("/api/production-houses/**").hasRole("EMPLOYEE")
            .antMatchers("/api/producers/**").hasRole("EMPLOYEE")
            .antMatchers("/api/contracts/**").hasRole("EMPLOYEE")
            .antMatchers(HttpMethod.POST, "/api/schedules/**").hasRole("EMPLOYEE")
            .antMatchers(HttpMethod.PUT, "/api/schedules/**").hasRole("EMPLOYEE")
            .antMatchers(HttpMethod.DELETE, "/api/schedules/**").hasRole("EMPLOYEE")
            .antMatchers(HttpMethod.POST, "/api/countries/**").hasRole("EMPLOYEE")
            .antMatchers(HttpMethod.PUT, "/api/countries/**").hasRole("EMPLOYEE")
            .antMatchers(HttpMethod.DELETE, "/api/countries/**").hasRole("EMPLOYEE")
            .antMatchers("/api/accounts/**").hasRole("EMPLOYEE")
            .antMatchers("/api/users/**").hasRole("EMPLOYEE")
            
            // Customer endpoints - feedback
            .antMatchers("/api/feedback/**").authenticated()
            
            // All other requests need to be authenticated
            .anyRequest().authenticated()
            
            .and()
            
            // Add JWT filter
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setExposedHeaders(Arrays.asList("Authorization"));
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
