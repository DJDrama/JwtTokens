package com.practice.jwttokens.controller.user

import com.practice.jwttokens.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import java.util.UUID

@RestController
@RequestMapping("/api/user")
class UserController(
    private val userService: UserService
) {

    @PostMapping
    fun create(@RequestBody userRequest: UserRequest): UserResponse =
        userService.createUser(user = userRequest.asUser())?.asUserResponse() ?: throw ResponseStatusException(
            HttpStatus.BAD_REQUEST,
            "Cannot create a user."
        )

    @GetMapping
    fun listAll(): List<UserResponse> = userService.findByAll().map {
        it.asUserResponse()
    }

    @GetMapping("/{uuid}")
    fun findByUUID(@PathVariable uuid: UUID) =
        userService.findByUUID(uuid = uuid)?.asUserResponse() ?: throw ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "Cannot find a user."
        )

    @DeleteMapping("/{uuid}")
    fun deleteUser(@PathVariable uuid: UUID): ResponseEntity<Boolean> {
        val isDeleted = userService.deleteByUUID(uuid = uuid)
        return if (isDeleted) ResponseEntity.noContent().build()
        else
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find a user.")
    }

}