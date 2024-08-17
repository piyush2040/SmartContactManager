package com.Smart.Config;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableWebSecurity
public class MyConfig {
	
	

	@Bean
	public UserDetailsService getUserDetailsService()
	{
		return new UserDetailsServiceImpl();
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider()
	{
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(this.getUserDetailsService());
		daoAuthenticationProvider.setPasswordEncoder(this.passwordEncoder());
		return daoAuthenticationProvider;
	}
	
//	@Bean
//	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//	    return authenticationConfiguration.getAuthenticationManager();
//	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
	{
		
			http
	        .csrf(csrf -> csrf.disable()) // Disabling CSRF with a lambda expression
	        .authorizeHttpRequests(auth -> auth
	            .requestMatchers("/admin/**").hasRole("ADMIN")
	            .requestMatchers("/User/**").hasRole("USER")
	            .requestMatchers("/**").permitAll()
	            .anyRequest()
	            .authenticated()
	        )
	        .authenticationProvider(this.authenticationProvider())
	        .formLogin(form -> form.loginPage("/signin") // Customize the login page URL
	        	    .defaultSuccessUrl("/", false) // Redirect to '/' if no previous request
	        	    .failureUrl("/login?error") // Customize the failure URL
	        	    .permitAll() // Allow everyone to access the login page // Configuring form login
	        	    );
		
		return http.build();
		
		
		
		
	}
	
	
	
	
	
}
