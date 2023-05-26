package com.moviebookingapp.Security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.moviebookingapp.filter.JwtRequestFilter;
import com.moviebookingapp.service.CustomerUserDetailsService;




/**
 This class is used for the security configuration. It extends the
 *          class WebSecurityConfigurerAdapter It will take care of the
 *          authentication and authorization based on the user credentials.
 *
 */
@SuppressWarnings("deprecation")
@EnableWebSecurity
@Configuration
public class SecurityConfig  implements WebMvcConfigurer {
	/**
	 * This is a private field of class {@link ManagerDetailsService}
	 * {@link ManagerDetailsService} class is used to load the user credentials
	 * from the database. Based on that database fetched user credentials
	 * authentication will be performed
	 */
	@Autowired
	private CustomerUserDetailsService managerDetailsService;
	/**
	 * This is a private field of class {@link JwtRequestFilter} This class extends
	 * {@link OncePerRequestFilter} so every request will first hit this filter
	 */
	
	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	
	
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration auth) throws Exception {

		return auth.getAuthenticationManager();
	}
	/**
	 * This methods used to override the credentials which spring automatically
	 * generates By using {@link AuthenticationManagerBuilder} object we are
	 * overriding the security credentials with our own given credentials It will
	 * call the userDetailsService method on the
	 * {@link AuthenticationManagerBuilder} class object and this method is present
	 * in class {@link AuthenticationManagerBuilder}
	 */
	
	@Bean
	public SecurityFilterChain filterchain(HttpSecurity http) throws Exception {
		http.csrf().disable().cors().and()
		.authorizeRequests().requestMatchers("/add","/users","/reset-password","/login","/register","/addmovie","/{moviename}/delete","/all","/movies/search/{moviename}","{username}/forgot").permitAll()
		//.antMatchers("/h2-console/**").permitAll()
		.anyRequest().authenticated()
		.and().sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.addFilterBefore(jwtRequestFilter,UsernamePasswordAuthenticationFilter.class);
		//http.headers().frameOptions().disable();
		return http.build();
	}
	/**
	 * This method is used to inject a {@link AuthenticationManager} type bean We
	 * are annotating this method with @Bean. @Bean annotation tells that a method
	 * produces a bean to be managed by the Spring container.
	 */
	
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10);
	}
	
	public void configure(AuthenticationManagerBuilder authenticationmanager) throws Exception {
		authenticationmanager.userDetailsService(managerDetailsService).passwordEncoder(passwordEncoder());
	}


}
