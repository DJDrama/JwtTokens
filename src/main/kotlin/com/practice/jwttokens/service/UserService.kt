package com.practice.jwttokens.service

import com.practice.jwttokens.model.User
import com.practice.jwttokens.repository.UserRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(
    private val userRepository: UserRepository
) {
    fun createUser(user: User): User? {
        val found = userRepository.findByEmail(email = user.email)
        return if (found == null) {
            userRepository.save(user = user)
            user
        } else {
            null
        }
    }

    fun findByUUID(uuid: UUID): User? = userRepository.findByUUID(uuid)

    fun findByAll() = userRepository.findAll()

    fun deleteByUUID(uuid: UUID): Boolean = userRepository.deleteByUUID(uuid = uuid)
}