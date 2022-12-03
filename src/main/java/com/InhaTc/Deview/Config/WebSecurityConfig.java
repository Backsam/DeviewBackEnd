package com.InhaTc.Deview.Config;


import com.InhaTc.Deview.Security.JwtAuthenticationFilter;
import com.InhaTc.Deview.User.Constant.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
public class WebSecurityConfig{

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    @Order(SecurityProperties.BASIC_AUTH_ORDER)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()
                    .disable()
                .httpBasic()
                    .disable()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)        //세션기반 아님 선언
                .and()
                .authorizeRequests()
                    .antMatchers("/**").permitAll()                     //인증  x
                    .antMatchers("/portfolio/write").hasRole(String.valueOf(UserRole.DEVELOPER))
                .anyRequest()
                    .authenticated();                                             //나머지 링크는 인증

        http.addFilterAfter(jwtAuthenticationFilter, CorsFilter.class);

        return http.build();
    }

}
