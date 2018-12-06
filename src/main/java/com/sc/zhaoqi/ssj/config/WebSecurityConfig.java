package com.sc.zhaoqi.ssj.config;

import com.sc.zhaoqi.ssj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig
        extends WebSecurityConfigurerAdapter
{
    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    @Autowired
    private EntryPointUnauthorizedHandler entryPointUnauthorizedHandler;
    @Autowired
    private RestAccessDeniedHandler restAccessDeniedHandler;
    @Autowired
    private UserService userService;

    @Override
    protected void configure(HttpSecurity http)
            throws Exception
    {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/", "/favicon.ico", "/hello", "/index", "/user/login", "/user/register",
                        "/v2/api-docs", "/swagger-ui.html", "/webjars/**", "/swagger-resources/**")
                .permitAll()
                .anyRequest().authenticated();
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        http.exceptionHandling().authenticationEntryPoint(entryPointUnauthorizedHandler).accessDeniedHandler(restAccessDeniedHandler);
    }

    @Bean
    public FilterRegistrationBean corsFilter()
    {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        // 设置你要允许的网站域名，如果全允许则设为 *
        config.addAllowedOrigin("*");
        // 如果要限制 HEADER 或 METHOD 请自行更改
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        // 这个顺序很重要哦，为避免麻烦请设置在最前
        bean.setOrder(0);
        return bean;
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService()
    {
        return userService;
    }

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder)
            throws Exception
    {
        authenticationManagerBuilder
                // 设置UserDetailsService
                .userDetailsService(userDetailsService())
                // 使用BCrypt进行密码的hash
                .passwordEncoder(passwordEncoder());
    }

    // 装载BCrypt密码编码器
    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception
    {
        auth.userDetailsService(userDetailsService());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean()
            throws Exception
    {
        return super.authenticationManagerBean();
    }

    /**
     * 自定401返回值
     */
    @Component
    public class EntryPointUnauthorizedHandler
            implements AuthenticationEntryPoint
    {

        @Override
        public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
        {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setStatus(401);
        }
    }

    /**
     * 自定403返回值
     */
    @Component
    public class RestAccessDeniedHandler
            implements AccessDeniedHandler
    {

        @Override
        public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e)
                throws IOException
        {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setStatus(403);
            PrintWriter writer = response.getWriter();
            writer.println("403");
        }
    }
}
