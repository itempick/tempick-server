package com.tempick.tempickserver.configuration.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
class SecurityConfig(
    private val securityProperties: SecurityProperties,
    private val jwtTokenProvider: JwtTokenProvider,
    private val userDetailsService: CustomUserDetailsService,
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf { it.disable() }

        http.httpBasic { it.disable() }

        http.headers { headers ->
            headers.frameOptions { it.sameOrigin() }
        }

        http.authorizeHttpRequests { authorize ->
            securityProperties.publicPaths.forEach { path ->
                authorize.requestMatchers(path).permitAll()
            }

            authorize.anyRequest().authenticated()
        }

        http.addFilterBefore(
            JwtAuthenticationFilter(jwtTokenProvider, userDetailsService),
            UsernamePasswordAuthenticationFilter::class.java,
        )

        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}
