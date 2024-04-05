package com.practice.jwttokens.service

import com.practice.jwttokens.config.JwtProperties
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.Date

@Service
class TokenService(
    jwtProperties: JwtProperties
) {
    private val secretKey = Keys.hmacShaKeyFor(
        /* bytes = */ jwtProperties.key.toByteArray()
    )

    fun generate(
        userDetails: UserDetails,
        expirationDate: Date,
        additionalClaims: Map<String, Any> = emptyMap()
    ): String {
        return Jwts.builder()
            .claims()
            .subject(userDetails.username)
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(expirationDate)
            .add(additionalClaims)
            .and()
            .signWith(secretKey)
            .compact()
    }

    fun extractEmail(token: String): String? =
        getAllClaims(token = token)
            .subject

    fun isExpired(token: String): Boolean {
        return getAllClaims(token = token)
            .expiration
            .before(Date(System.currentTimeMillis()))
    }

    fun isValid(token: String, userDetails: UserDetails): Boolean {
        val email = extractEmail(token = token)
        return userDetails.username == email && !isExpired(token = token)
    }

    private fun getAllClaims(token: String): Claims {
        val parser = Jwts.parser()
            .verifyWith(secretKey)
            .build()

        return parser
            .parseSignedClaims(token)
            .payload
    }

}