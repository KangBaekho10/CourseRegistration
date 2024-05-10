package org.example.courseregistration.domain.courseapplication.dto

import org.example.courseregistration.domain.course.dto.CourseResponse
import org.example.courseregistration.domain.user.dto.UserResponse

data class CourseApplicationResponse (
    val id: Long,
    val course: CourseResponse,
    val user: UserResponse,
    val status: String?
)
