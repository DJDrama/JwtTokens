package com.practice.jwttokens.controller.auth

data class AuthenticationRequest (
    val email: String,
    val password: String,
)
