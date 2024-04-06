package com.practice.jwttokens.service

import com.practice.jwttokens.repository.UserRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service


typealias ApplicationUser = com.practice.jwttokens.model.User

@Service
class CustomUserDetailService(
    private val userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        return userRepository.findByEmail(email = username)
            ?.mapToUserDetails()
            ?: throw UsernameNotFoundException("User Not Found!")
    }

    private fun ApplicationUser.mapToUserDetails(): UserDetails{
        return User.builder()
            .username(this.email)
            .password(this.password)
            .roles(this.role.name)
            .build()
    }

}