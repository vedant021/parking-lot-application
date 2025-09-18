package org.interview.config;

import org.interview.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.*;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Value("${app.admin.email:ved1129114@gmail.com}")
    private String adminEmail;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * SecurityFilterChain defines the security rules for API endpoints and OAuth2 login.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/h2-console/**", "/swagger-ui/**", "/v3/api-docs/**", "/auth/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2Login()
                .userInfoEndpoint()
                .oidcUserService(customOidcUserService())
                .userService(customOauth2UserService())
                .and()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                .and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .headers().frameOptions().disable();
        return http.build();
    }

    /**
     * OIDC user service to map Google users to roles (ADMIN/USER).
     */
    private OidcUserService customOidcUserService() {
        final OidcUserService delegate = new OidcUserService();
        return new OidcUserService() {
            @Override
            public OidcUser loadUser(OidcUserRequest userRequest) {
                OidcUser oidcUser = delegate.loadUser(userRequest);
                Set<GrantedAuthority> mappedAuthorities = new HashSet<GrantedAuthority>();
                String email = oidcUser.getEmail();
                if (adminEmail.equalsIgnoreCase(email)) {
                    mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                }
                mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
                return new DefaultOidcUser(
                        new ArrayList<GrantedAuthority>(mappedAuthorities),
                        oidcUser.getIdToken(),
                        oidcUser.getUserInfo()
                );
            }
        };
    }

    private OAuth2UserService<OAuth2UserRequest, OAuth2User> customOauth2UserService() {
        final DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
        return new OAuth2UserService<OAuth2UserRequest, OAuth2User>() {
            @Override
            public OAuth2User loadUser(OAuth2UserRequest userRequest) {
                OAuth2User oAuth2User = delegate.loadUser(userRequest);
                Map<String, Object> attributes = oAuth2User.getAttributes();
                Set<GrantedAuthority> mappedAuthorities = new HashSet<GrantedAuthority>();
                String email = (String) attributes.get("email");
                if (adminEmail.equalsIgnoreCase(email)) {
                    mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                }
                mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
                return new DefaultOAuth2User(
                        new ArrayList<GrantedAuthority>(mappedAuthorities),
                        attributes,
                        "email"
                );
            }
        };
    }
}
