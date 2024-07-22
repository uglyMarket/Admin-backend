package com.sparta.uglymarket.config;

import com.sparta.uglymarket.service.CustomUserDetailsService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // 이 클래스가 Spring의 설정 클래스임을 나타냅니다. 주로 빈 정의와 설정을 포함합니다.
@EnableWebSecurity // 웹 보안을 활성화합니다. 이 어노테이션은 Spring Security 설정을 활성화하는 데 사용됩니다.
@RequiredArgsConstructor // Lombok 어노테이션으로, final 필드나 @NonNull 필드에 대한 생성자를 자동으로 생성합니다.
public class SecurityConfig {

    // CustomUserDetailsService를 주입받습니다. 이 서비스는 사용자 세부 정보를 로드하는 데 사용됩니다.
    private final CustomUserDetailsService userDetailsService;
    // CustomAuthenticationFailureHandler를 주입받습니다. 이 핸들러는 인증 실패 시 사용자 정의 응답을 처리합니다.
    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Bean // 이 메서드의 반환값을 스프링 컨텍스트에 빈으로 등록합니다.
    public PasswordEncoder passwordEncoder() {
        // 비밀번호 암호화를 위한 BCryptPasswordEncoder를 빈으로 등록합니다.
        return new BCryptPasswordEncoder();
    }

    @Bean // 이 메서드의 반환값을 스프링 컨텍스트에 빈으로 등록합니다.
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // HttpSecurity를 통해 보안 설정을 구성합니다.
        http
                // CSRF 보호를 비활성화합니다. CSRF 보호는 크로스 사이트 요청 위조를 방지하지만, API 서버에서는 주로 비활성화합니다.
                .csrf(csrf -> csrf.disable())
                // 요청에 대한 권한을 설정합니다.
                .authorizeHttpRequests(authorize -> authorize
                        // /api/admin/register와 /api/admin/login 경로에 대한 접근을 허용합니다. 즉, 인증 없이 접근할 수 있습니다.
                        .requestMatchers("/api/admin/register", "/api/admin/login").permitAll()
                        // 그 외의 모든 요청은 인증된 사용자만 접근할 수 있도록 설정합니다.
                        .anyRequest().authenticated()
                )
                // 폼 로그인을 설정합니다.
                .formLogin(formLogin -> formLogin
                        // 로그인 처리 URL을 설정합니다. 이 URL로 POST 요청이 오면 인증을 시도합니다.
                        .loginProcessingUrl("/api/admin/login")
                        // 로그인 성공 시 JSON 응답을 반환합니다.
                        .successHandler((request, response, authentication) -> {
                            response.setStatus(HttpServletResponse.SC_OK);
                            response.setContentType("application/json");
                            response.getWriter().write("{\"message\": \"로그인 성공\"}");
                        })
                        // 로그인 실패 시 JSON 응답을 반환합니다.
                        .failureHandler((request, response, exception) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.setContentType("application/json");
                            response.getWriter().write("{\"message\": \"정보가 일치하지 않습니다. 로그인 실패\"}");
                        })
                )
                // 로그아웃 설정을 구성합니다.
                .logout(logout -> logout
                        // 로그아웃 처리 URL을 설정합니다. 이 URL로 POST 요청이 오면 로그아웃을 시도합니다.
                        .logoutUrl("/api/admin/logout")
                        // 로그아웃 성공 시 JSON 응답을 반환합니다.
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.setStatus(HttpServletResponse.SC_OK);
                            response.setContentType("application/json");
                            response.getWriter().write("{\"message\": \"로그아웃 성공\"}");
                        })
                );
        // 설정한 SecurityFilterChain을 반환하여 Spring Security에 적용합니다.
        return http.build();
    }
    @Bean // 이 메서드의 반환값을 스프링 컨텍스트에 빈으로 등록합니다.
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        // AuthenticationConfiguration을 통해 AuthenticationManager를 빈으로 등록합니다.
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean // 이 메서드의 반환값을 스프링 컨텍스트에 빈으로 등록합니다.
    public WebSecurityCustomizer webSecurityCustomizer() {
        // 특정 요청 경로를 보안 설정에서 제외합니다. 주로 정적 리소스(css, js, 이미지 등)에 사용됩니다.
        return (web) -> web.ignoring().requestMatchers("/resources/**");
    }
}
