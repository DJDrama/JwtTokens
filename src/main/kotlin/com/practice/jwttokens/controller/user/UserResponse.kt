package com.practice.jwttokens.controller.user

import com.practice.jwttokens.model.User
import java.util.UUID

data class UserResponse(
    val id: UUID,
    val email: String,
)


fun User.asUserResponse() =
    UserResponse(
        id = id,
        email = email
    )
