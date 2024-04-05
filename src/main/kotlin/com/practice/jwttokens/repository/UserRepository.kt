package com.practice.jwttokens.repository

import com.practice.jwttokens.model.Role
import com.practice.jwttokens.model.User
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class UserRepository {

    private val users = mutableListOf(
        User(id = UUID.randomUUID(), email = "email-1@gmail.com", password = "pass1", role = Role.USER),
        User(id = UUID.randomUUID(), email = "email-2@gmail.com", password = "pass2", role = Role.ADMIN),
        User(id = UUID.randomUUID(), email = "email-3@gmail.com", password = "pass3", role = Role.USER)
    )

    fun save(user: User): Boolean = users.add(user)

    fun findByEmail(email: String): User? = users.firstOrNull { it.email == email }

    fun findByUUID(uuid: UUID): User? = users.firstOrNull { it.id == uuid }

    fun findAll() = users

    fun deleteByUUID(uuid: UUID): Boolean {
        val foundUser = findByUUID(uuid = uuid)
        return foundUser?.let {
            users.remove(it)
        } ?: false
    }
}