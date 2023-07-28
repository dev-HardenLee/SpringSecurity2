package com.example.test.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Configuration
@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
@Log4j2
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final UserDetailsService userDetailService;

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().mvcMatchers("/favicon.ico");
	}// configure
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("user" ).password("{noop}1111").roles("USER" );
	}// configure

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/login").permitAll()
			.antMatchers("/error").permitAll()
			.antMatchers("/user" ).hasRole("USER")
			.antMatchers("/admin").hasRole("ADMIN")
			.anyRequest().authenticated();
		
		http.formLogin()
			.successHandler((request, response, authentication) -> {
				// 세선에서 이미 저장되어 있는 이전 요청 정보를 추출
				HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
				SavedRequest            savedRequest = requestCache.getRequest(request, response);
				String                  redirectUrl  = savedRequest.getRedirectUrl();
				
				log.info("redirectUrl ===> " + redirectUrl);
				
				response.sendRedirect(redirectUrl);
			});
		
		http.logout();
		
		http.exceptionHandling();
//			.authenticationEntryPoint((request, response, authException) -> {
//				// ** AuthenticationEntiryPoint를 직접 구현하게 되면 우리가 만든 로그인 페이지로 이동하게 된다.
//				// 스프링 시큐리티가 기본적으로 제공하는 로그인 페이지가 아니다.
//				// DefaultLoginPageConfigurer 클래스 참고.
//				log.info("### authenticationEntiryPoint..");
//				
//				response.sendRedirect("/login");
//			})
//			.accessDeniedHandler((request, response, accessDeniedException) -> {
//				log.info("### AccessDenied...");
//				
//				accessDeniedException.printStackTrace();
				
//				response.sendRedirect("/denied");
//				response.sendError(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.getReasonPhrase());
//			});
		
	}// configure
	
	
	
}// SecurityConfig




























