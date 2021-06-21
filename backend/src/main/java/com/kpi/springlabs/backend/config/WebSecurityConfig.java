package com.kpi.springlabs.backend.config;

import com.kpi.springlabs.backend.security.CustomAccessDeniedHandler;
import com.kpi.springlabs.backend.security.token.JwtLogoutHandler;
import com.kpi.springlabs.backend.security.token.JwtTokenFilter;
import com.kpi.springlabs.backend.security.token.JwtTokenProvider;
import com.kpi.springlabs.backend.security.user.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthenticationEntryPoint unauthorizedHandler;
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService userDetailsService;
    private final JwtLogoutHandler jwtLogoutHandler;
    private final CustomAccessDeniedHandler accessDeniedHandler;

    @Autowired
    public WebSecurityConfig(AuthenticationEntryPoint unauthorizedHandler, JwtTokenProvider jwtTokenProvider,
                             CustomUserDetailsService userDetailsService, JwtLogoutHandler jwtLogoutHandler,
                             CustomAccessDeniedHandler accessDeniedHandler) {
        this.unauthorizedHandler = unauthorizedHandler;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
        this.jwtLogoutHandler = jwtLogoutHandler;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
                .and()
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(unauthorizedHandler)
                .accessDeniedHandler(accessDeniedHandler)
                .and()
                .authorizeRequests()
                .antMatchers("/api/auth/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/api/auth/logout", HttpMethod.DELETE.name()))
                .addLogoutHandler(jwtLogoutHandler)
                .logoutSuccessUrl("/api/auth/logout?success=true");

        http.addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
        web.ignoring().antMatchers("/v2/api-docs/**", "/swagger-resources/**", "/swagger-ui/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public JwtTokenFilter jwtTokenFilter() {
        return new JwtTokenFilter(jwtTokenProvider, userDetailsService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
