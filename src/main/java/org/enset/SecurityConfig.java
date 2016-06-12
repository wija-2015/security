package org.enset;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity  //Pour fonctionner la configuration du spring security 
@EnableGlobalMethodSecurity(securedEnabled=true) //Pour proteger les methodes qui ont deja declarer dans la couche Rest avec @secured(role)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	public void globalConfig(AuthenticationManagerBuilder auth,DataSource dataSource) throws Exception{
	//L'authentification se trouve au niv du memoire(dans le code java, nn dans une BD)
	//auth.inMemory: juste pour declarer les ressources qui ont le droit de se connecter 
	/*auth.inMemoryAuthentication().withUser("admin").password("123").roles("ADMIN","PROF");
	auth.inMemoryAuthentication().withUser("sco1").password("123").roles("SCOLARITE");
	auth.inMemoryAuthentication().withUser("prof1").password("123").roles("PROF");
	auth.inMemoryAuthentication().withUser("et1").password("123").roles("ETUDIANT"); */
	
	auth.jdbcAuthentication()
		.dataSource(dataSource)
		.usersByUsernameQuery("select username as principal, password as credentials, true from users where username = ?")
		.authoritiesByUsernameQuery("select users_username as principal, roles_role as role from users_roles where users_username = ?")
		.rolePrefix("ROLE_");
	
	
	//auth.ldapAuthentication();   s'authentifier avec google ou facebook
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	//toutes les req doivent etre authentifiées
	http
	  .csrf().disable()
	  .authorizeRequests()
	  .antMatchers("/css/**","/js/**","/images/**").permitAll()
	  .anyRequest()
	  .authenticated()
	.and()
	  .formLogin()
	  .loginPage("/loginUrl")   //la fct respo de l'affichage de la page de login
	  .permitAll()  
	  .defaultSuccessUrl("/index.html")
	.and()
	  .logout()
	  .invalidateHttpSession(true)
	  .logoutUrl("/logout")
	  .permitAll();
	  
	/*.failureUrl("/error.html")
	 .permitAll()
	
	*/
	}

}
