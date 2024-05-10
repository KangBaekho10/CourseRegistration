package org.example.courseregistration.domain.user.service

import org.example.courseregistration.domain.exception.ModelNotFoundException
import org.example.courseregistration.domain.user.dto.SignUpRequest
import org.example.courseregistration.domain.user.dto.UpdateUserProfile
import org.example.courseregistration.domain.user.dto.UserResponse
import org.example.courseregistration.domain.user.model.Profile
import org.example.courseregistration.domain.user.model.User
import org.example.courseregistration.domain.user.model.UserRole
import org.example.courseregistration.domain.user.model.toResponse
import org.example.courseregistration.domain.user.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
    private val userRepository: UserRepository
): UserService {

    @Transactional
    override fun signUp(signUpRequest: SignUpRequest): UserResponse {
        if (userRepository.existsByEmail(signUpRequest.email)) {
            throw IllegalStateException("Email is already in use")
        }

        return userRepository.save(
            User(
                email = signUpRequest.email,
                // TODO: 비밀번호 암호화
                password = signUpRequest.password,
                profile = Profile(
                    nickname = signUpRequest.nickname
                ),
                role = when (signUpRequest.role) {
                    UserRole.STUDENT.name -> UserRole.STUDENT
                    UserRole.TUTOR.name -> UserRole.TUTOR
                    else -> throw IllegalArgumentException("Invalid role")
                }
            )
        ).toResponse()
    }

    @Transactional
    override fun updateUserProfile(userId: Long, updateUserProfile: UpdateUserProfile): UserResponse {
        val user = userRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("User", userId)
        user.profile = Profile(
            nickname = updateUserProfile.nickname
        )

        return userRepository.save(user).toResponse()
    }

}