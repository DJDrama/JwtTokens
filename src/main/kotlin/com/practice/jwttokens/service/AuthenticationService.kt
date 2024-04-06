package com.practice.jwttokens.service

import com.practice.jwttokens.config.JwtProperties
import com.practice.jwttokens.controller.auth.AuthenticationRequest
import com.practice.jwttokens.controller.auth.AuthenticationResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthenticationService(
    private val authManager: AuthenticationManager,
    private val userDetailsService: CustomUserDetailService,
    private val tokenService: TokenService,
    private val jwtProperties: JwtProperties
) {


    fun authentication(authRequest: AuthenticationRequest): AuthenticationResponse {
        authManager.authenticate(
            UsernamePasswordAuthenticationToken(
                /* principal = */ authRequest.email,
                /* credentials = */ authRequest.password
            )
        )

        val user = userDetailsService.loadUserByUsername(authRequest.email)
        val accessToken = tokenService.generate(
            userDetails = user,
            expirationDate = Date(System.currentTimeMillis() + jwtProperties.accessTokenExpiration)
        )
        return AuthenticationResponse(accessToken = accessToken)
    }

}
