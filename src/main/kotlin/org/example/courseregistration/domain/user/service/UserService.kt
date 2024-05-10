package org.example.courseregistration.domain.user.service

import org.example.courseregistration.domain.user.dto.SignUpRequest
import org.example.courseregistration.domain.user.dto.UpdateUserProfile
import org.example.courseregistration.domain.user.dto.UserResponse

interface UserService {

    fun signUp(signUpRequest: SignUpRequest): UserResponse

    fun updateUserProfile(userId: Long, updateUserProfile: UpdateUserProfile): UserResponse
}