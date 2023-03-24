package com.cos.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // 빈등록 (IoC관리)
@EnableWebSecurity // 시큐리티 필터가 등록이 된다.
//Controller에서 특정 권한이 있는 유저만 접근을 허용하려면 @PreAuthorize 어노테이션을 사용하는데, 해당 어노테이션을 활성화 시키는 어노테이션이다.
public class SecurityConfig {

	// 비밀번호를 해쉬화하여 암호화한다.
	@Bean
	public BCryptPasswordEncoder encodePWD() {		
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.csrf().disable() // csrf 토큰 비활성화 (테스트시 걸어두는 게 좋음)
			.authorizeHttpRequests()
	        .requestMatchers("/", "/auth/**", "/js/**", "/css/**", "/image/**", "/dummy/**")
	        .permitAll()
	        .requestMatchers("/", "/auth/**", "/js/**", "/css/**", "/image/**", "/dummy/**")
	        .authenticated()
            .requestMatchers("/manager/**").hasAnyRole("MANAGER", "ADMIN")
            .requestMatchers("/admin/**").hasRole("ADMIN")
            .anyRequest().permitAll()
		.and()
			.formLogin()
			.loginPage("/loginForm") // 이동하는 로그인 화면은 단일로 지정하기 /auth/loginForm 과 같이 auth가 더 있으면 인식하지 못함.
			.loginProcessingUrl("/auth/loginProc") // 스프링 시큐리티가 해당 주소로 요청오는 로그인을 가로채서 대신 로그인 해준다.
			.defaultSuccessUrl("/"); // 로그인이 정상으로 끝나면 가는 위치
//			.failureUrl("/fail"); // 로그인 실패시 가는 위치
	
		return http.build();
	}
}
