package javaPack.todo.config;

import javaPack.todo.Security.JWTAuthEntrypoint;
import javaPack.todo.Security.JwtAuthFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@AllArgsConstructor
@EnableMethodSecurity
public class SpringSecurityConfig {

    private  UserDetailsService userDetailsService;
    private JwtAuthFilter jwtAuthFilter;
    private JWTAuthEntrypoint jwtAuthEntrypoint;

    @Bean
    public static PasswordEncoder PE()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain SFC(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable()) // Disable CSRF entirely for stateless applications
                .authorizeHttpRequests((authorize) -> {
//                    authorize.requestMatchers(HttpMethod.POST, "/site/**").hasRole("ADMIN");
//                    authorize.requestMatchers(HttpMethod.PUT, "/site/**").hasRole("ADMIN");
//                    authorize.requestMatchers(HttpMethod.DELETE, "/site/**").hasRole("ADMIN");
//                    authorize.requestMatchers(HttpMethod.GET, "/site/**").hasAnyRole("ADMIN", "USER");
//                    authorize.requestMatchers(HttpMethod.PATCH, "/site/**").hasAnyRole("ADMIN", "USER");
                    authorize.requestMatchers("/site/auth/**").permitAll();
                    authorize.requestMatchers(HttpMethod.OPTIONS,"/**").permitAll();
                    authorize.anyRequest().authenticated();
                }).httpBasic(Customizer.withDefaults());
        http.exceptionHandling(exception -> exception
                .authenticationEntryPoint(jwtAuthEntrypoint));
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
//    @Bean
//    public UserDetailsService UDS()
//    {
//        UserDetails mrudula= User.builder()
//                .username("mrudula")
//                .password(PE().encode("mru@123"))
//                .roles("USER")
//                .build();
//        UserDetails manohar= User.builder()
//                .username("manohar")
//                .password(PE().encode("manu@123"))
//                .roles("ADMIN")
//                .build();
//        UserDetails pavani= User.builder()
//                .username("pavani")
//                .password(PE().encode("pavani@123"))
//                .roles("MANAGER")
//                .build();
//        return new InMemoryUserDetailsManager(manohar,mrudula,pavani);
//    }
}
