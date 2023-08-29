package com.devtalk.member.memberservice.global.config;

import com.devtalk.member.memberservice.global.jwt.JwtAccessDeniedHandler;
import com.devtalk.member.memberservice.global.jwt.JwtAuthenticationEntryPoint;
import com.devtalk.member.memberservice.global.jwt.JwtAuthenticationFilter;
import com.devtalk.member.memberservice.global.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        // 비밀번호를 DB에 저장할 때 사용할 암호화
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // ACL(Access Control List, 접근 제어 목록)의 예외 URL 설정
        return (web -> web
                .ignoring()
                .requestMatchers("/members/**", "/consulter", "/consultant", "/login", "/logout"));
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        // 인터셉터로 요청을 안전하게 보호하는 방법 설정
//        http
//                .csrf().disable()
//                .httpBasic().disable()
//                .formLogin().disable()
//                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                // 예외 처리
//                .and()
//                .exceptionHandling()
//                .authenticationEntryPoint(jwtAuthenticationEntryPoint) // customEntryPoint 401 error handling
//                .accessDeniedHandler(jwtAccessDeniedHandler) // customAccessDeniedHandler 403 error handling
//
//                .and()
//                .authorizeHttpRequests() // 인증이 필요한 것들
//                .antMatchers("/mypage/**").authenticated()
//                .anyRequest().permitAll() // 그 외 나머지는 인증 X
//
//                .and()
//                .headers()
//                .frameOptions().sameOrigin();
//
//        return http.build();
        return httpSecurity
                .httpBasic((httpBasic) -> httpBasic.disable())
                .csrf((csrf) -> csrf.disable())
                .sessionManagement((sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin((formLogin) -> formLogin.disable())
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests((req) -> req.requestMatchers("/mypage/**").authenticated()
                .anyRequest().permitAll())
                .exceptionHandling((e) -> e.authenticationEntryPoint(jwtAuthenticationEntryPoint)
                        .accessDeniedHandler(jwtAccessDeniedHandler))
                .build();

    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
