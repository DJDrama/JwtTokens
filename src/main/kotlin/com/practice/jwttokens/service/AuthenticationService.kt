package com.practice.jwttokens.service

import com.practice.jwttokens.config.JwtProperties
import com.practice.jwttokens.controller.auth.AuthenticationRequest
import com.practice.jwttokens.controller.auth.AuthenticationResponse
import com.practice.jwttokens.repository.RefreshTokenRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthenticationService(
    private val authManager: AuthenticationManager,
    private val userDetailsService: CustomUserDetailService,
    private val tokenService: TokenService,
    private val jwtProperties: JwtProperties,
    private val refreshTokenRepository: RefreshTokenRepository,

    ) {


    fun authentication(authRequest: AuthenticationRequest): AuthenticationResponse {
        authManager.authenticate(
            UsernamePasswordAuthenticationToken(
                /* principal = */ authRequest.email,
                /* credentials = */ authRequest.password
            )
        )

        val user = userDetailsService.loadUserByUsername(authRequest.email)
        val accessToken = generateAccessToken(user)
        val refreshToken = generateRefreshToken(user)

        refreshTokenRepository.save(token = refreshToken, userDetails = user)
        return AuthenticationResponse(
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }

    fun refreshAccessToken(token: String): String? {
        val extractedEmail = tokenService.extractEmail(token = token)
        return extractedEmail?.let { email ->
            val currentUserDetails = userDetailsService.loadUserByUsername(username = email)
            val refreshTokenUserDetails = refreshTokenRepository.findUserDetailsByToken(token = token)
            if (!tokenService.isExpired(token = token)
                && currentUserDetails.username == refreshTokenUserDetails?.username
            ) {
                generateAccessToken(user = currentUserDetails)
            } else null
        }
    }

    private fun generateRefreshToken(user: UserDetails) = tokenService.generate(
        userDetails = user,
        expirationDate = Date(System.currentTimeMillis() + jwtProperties.refreshTokenExpiration)
    )

    private fun generateAccessToken(user: UserDetails) = tokenService.generate(
        userDetails = user,
        expirationDate = Date(System.currentTimeMillis() + jwtProperties.accessTokenExpiration)
    )


}
