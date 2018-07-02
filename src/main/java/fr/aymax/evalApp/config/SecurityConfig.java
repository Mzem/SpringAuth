package fr.aymax.evalApp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import fr.aymax.evalApp.service.AppUserDetailsService;

/**
 * We need to annotate our security configuration class with @EnableWebSecurity that helps to configure spring security from WebSecurityConfigurer class. 
 * To override any security method we need to extend WebSecurityConfigurerAdapter class.
 * We are using @Secured annotation to secure service layer. 
 * This annotation will only be effective if we use @EnableGlobalMethodSecurity with the attribute securedEnabled=true annotated at class level with @Configuration annotation. 
*/
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter 
{
	@Autowired
	private AppUserDetailsService appUserDetailsService;
	
	/**
	 * We override configure() method from WebSecurityConfigurerAdapter class that is used to configure HttpSecurity class. 
	 * Within this method we are performing spring security login, logout and exception handling configuration. 
	 * In spring security using JavaConfig, CSRF protection is enabled by default. CSRF protection is Cross Site Request Forgery. If we want to disable it we can do it using http.csrf().disable() . 
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception 
	{	
		http.authorizeRequests()	//restrict acces based on the servelet request coming in
			 //.filterSecurityInterceptorOncePerRequest(false)
			.antMatchers("/").permitAll()
			//.antMatchers("/user/**").hasAnyRole("ADMIN","EMPLOYE","MANAGER")
			.anyRequest().authenticated()	//any request must be authentificated
		.and()
			.formLogin()	
			.loginPage("/login")	//my custom login page
			.loginProcessingUrl("/authenticateUser")	//auth process, handeled by spring
			.usernameParameter("app_username")
            .passwordParameter("app_password")
            .defaultSuccessUrl("/")
			.permitAll()	//anyone can see the login page
		.and()
			.logout().permitAll();/*
		.and()
			.exceptionHandling() //exception handling configuration
			.accessDeniedPage("/error");*/
	}
	
	/**
	 * To authenticate user, we will use custom UserDetailsService which in turn will use hibernate ORM to interact with database. 
	 * Using configureGlobal() we will configure our custom UserDetailsService instance. For this purpose spring security provides AuthenticationManagerBuilder. 
	 */
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(appUserDetailsService).passwordEncoder(passwordEncoder());
	}
    
    /**
     * We configure password encoder. We are using BCryptPasswordEncoder for password encoding.
     */
    @Bean
	public PasswordEncoder passwordEncoder() {
		//While instantiating BCryptPasswordEncoder we can optionally pass strength or we can also pass SecureRandom instance to its constructor. The default strength is 10.
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder;
	}
}






