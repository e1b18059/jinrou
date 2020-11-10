package oit.is.offline.jinrou.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class JinrouAuthConfiguration extends WebSecurityConfigurerAdapter {

  /**
   * 誰がログインできるか(認証処理)
   */
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
  auth.inMemoryAuthentication().withUser("user1").password(passwordEncoder().encode("pAssw0rd")).roles("USER");
  auth.inMemoryAuthentication().withUser("user2").password(passwordEncoder().encode("pAssw0rd")).roles("USER");
  auth.inMemoryAuthentication().withUser("user3").password(passwordEncoder().encode("pAssw0rd")).roles("USER");
  auth.inMemoryAuthentication().withUser("user4").password(passwordEncoder().encode("pAssw0rd")).roles("USER");
  auth.inMemoryAuthentication().withUser("user5").password(passwordEncoder().encode("pAssw0rd")).roles("USER");
  auth.inMemoryAuthentication().withUser("user6").password(passwordEncoder().encode("pAssw0rd")).roles("USER");
  auth.inMemoryAuthentication().withUser("user7").password(passwordEncoder().encode("pAssw0rd")).roles("USER");
  auth.inMemoryAuthentication().withUser("user8").password(passwordEncoder().encode("pAssw0rd")).roles("USER");
  auth.inMemoryAuthentication().withUser("user9").password(passwordEncoder().encode("pAssw0rd")).roles("USER");
  auth.inMemoryAuthentication().withUser("user10").password(passwordEncoder().encode("pAssw0rd")).roles("USER");

  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http.formLogin();

    http.authorizeRequests().antMatchers("/**").authenticated();

    http.csrf().disable();
    http.headers().frameOptions().disable();

    http.logout().logoutSuccessUrl("/");
  }

}
