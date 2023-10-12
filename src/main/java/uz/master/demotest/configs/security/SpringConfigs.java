package uz.master.demotest.configs.security;

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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        jsr250Enabled = true,
        securedEnabled = true,
        prePostEnabled = true
)
public class SpringConfigs extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder encoder;
    private final UserDetailsService service;

    public SpringConfigs(PasswordEncoder encoder, UserDetailsService service) {
        this.encoder = encoder;
        this.service = service;
    }
    public static final String[] WHITE_LIST = {
             "/auth/login","/auth/forgot","/auth/reset/**", "/index/index"
    };
    public static final String[] WHITE_LIST_RESOURCES = {
            "/css/**", "/webjars/**", "/js/**","/error", "/images/**", "/assets/**"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http

                .csrf().disable()
                .cors().disable()

                .authorizeRequests(expressionInterceptUrlRegistry -> expressionInterceptUrlRegistry
                        .antMatchers(WHITE_LIST).permitAll()
                        .anyRequest().authenticated())
                .formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer
                        .permitAll()
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/auth/login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/", true)
                )
                .rememberMe(httpSecurityRememberMeConfigurer -> httpSecurityRememberMeConfigurer
                        .key("gsbetest")
                        .rememberMeParameter("remember-me")
                        .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(10))
                ).logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer
                        .logoutUrl("/auth/logout")
                        .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout","GET"))
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID", "remember-me")
                )
                .exceptionHandling()
                .accessDeniedPage("/error/403.html");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(service).passwordEncoder(encoder);
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(WHITE_LIST_RESOURCES);
    }
}
