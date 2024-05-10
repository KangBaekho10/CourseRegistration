package org.example.courseregistration.domain.user.dto

data class UserResponse (
    var id : Long,
    val email: String,
    val nickname: String,
    val role: String
)
