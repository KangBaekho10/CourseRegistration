package org.example.courseregistration.domain.courseapplication.model

import jakarta.persistence.*
import org.example.courseregistration.domain.course.model.Course
import org.example.courseregistration.domain.course.model.toResponse
import org.example.courseregistration.domain.courseapplication.dto.CourseApplicationResponse
import org.example.courseregistration.domain.user.model.User
import org.example.courseregistration.domain.user.model.toResponse

@Entity
@Table(name = "course_application")
class CourseApplication(
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    var status: CourseApplicationStatus = CourseApplicationStatus.PENDING,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    val course: Course,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    fun isProceeded(): Boolean {
        return status != CourseApplicationStatus.PENDING
    }

    fun accept() {
        status = CourseApplicationStatus.ACCEPTED
    }

    fun reject() {
        status = CourseApplicationStatus.REJECTED
    }
}

fun CourseApplication.toResponse(): CourseApplicationResponse {
    return CourseApplicationResponse(
        id = id!!,
        course = course.toResponse(),
        user = user.toResponse(),
        status = status.name
    )
}