package com.practice.jwttokens.controller.user

import com.practice.jwttokens.model.Role
import com.practice.jwttokens.model.User
import java.util.UUID

data class UserRequest(
    val email: String,
    val password: String
)

fun UserRequest.asUser() = User(
    id = UUID.randomUUID(),
    email = email,
    password = password,
    role = Role.USER
)