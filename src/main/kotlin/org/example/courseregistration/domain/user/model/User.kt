package org.example.courseregistration.domain.user.model

import jakarta.persistence.*
import org.example.courseregistration.domain.courseapplication.model.CourseApplication
import org.example.courseregistration.domain.user.dto.UserResponse

@Entity
@Table(name = "app_user")
class User(
    @Column(name = "email", nullable = false)
    val email: String,

    @Column(name = "password", nullable = false)
    val password: String,

    @Embedded
    var profile: Profile,

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    val role: UserRole,

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    val courseApplications: MutableList<CourseApplication> = mutableListOf()
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}

fun User.toResponse(): UserResponse {
    return UserResponse(
        id = id!!,
        nickname = profile.nickname,
        email = email,
        role = role.name
    )
}