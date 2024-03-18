package sc.ict.board.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import sc.ict.board.jwt.CustomLogoutFilter;
import sc.ict.board.jwt.JWTFilter;
import sc.ict.board.jwt.JWTUtil;
import sc.ict.board.jwt.LoginFilter;
import sc.ict.board.repository.RefreshRepository;

import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;

    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration,JWTUtil jwtUtil, RefreshRepository refreshRepository) {

        this.authenticationConfiguration = authenticationConfiguration;
        this.jwtUtil = jwtUtil;
        this.refreshRepository = refreshRepository;
    }

    //AuthenticationManager는 사용자가 누구인지 확인하는 역할
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //HTTP 보안 설정을 구성
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws  Exception {
        // CORS 설정을 구성합니다.
        http
                .cors((corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {

                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

                        CorsConfiguration configuration = new CorsConfiguration();

                        configuration.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
                        configuration.setAllowedMethods(Collections.singletonList("*"));
                        configuration.setAllowCredentials(true);
                        configuration.setAllowedHeaders(Collections.singletonList("*"));
                        configuration.setMaxAge(3600L);

                        configuration.setExposedHeaders(Collections.singletonList("Authorization"));

                        return configuration;
                    }
                })));

        // CSRF 보호를 비활성화
        http
                .csrf(AbstractHttpConfigurer::disable);

        // 폼 로그인을 비활성화
        http
                .formLogin(AbstractHttpConfigurer::disable);

        // HTTP 기본 인증을 비활성화
        http
                .httpBasic(AbstractHttpConfigurer::disable);

        // 인증 및 권한 부여 설정을 구성 ( ex.'localhost:3000/login'에는 토큰이 없어도 접근 가능 )
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/login", "/", "/join").permitAll()
                        // ( ex.'localhost:3000/login'에는 role이 ADMIN만 접근 가능 )
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .requestMatchers("/reissue").permitAll()
                        .anyRequest().authenticated());

        // JWTFilter를 UsernamePasswordAuthenticationFilter 이전에 추가
        http
                .addFilterBefore(new JWTFilter(jwtUtil, refreshRepository), UsernamePasswordAuthenticationFilter.class);

        // LoginFilter를 UsernamePasswordAuthenticationFilter 위치에 추가
        http
                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil, refreshRepository), UsernamePasswordAuthenticationFilter.class);

        // CustomLogoutFilter를 LogoutFilter 이전에 추가
        http
                .addFilterBefore(new CustomLogoutFilter(jwtUtil, refreshRepository), LogoutFilter.class);

        // 세션 관리를 구성하고 세션 생성 정책을 한 번의 요청과 응답이 끝나면 그 요청에 대한 정보를 기억하지 않는다는 의미
        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        //스프링 시큐리티의 설정을 마무리하고 HttpSecurity 객체를 빌드하여 반환하는 역할
        return http.build();
    }
}
