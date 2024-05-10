package org.example.courseregistration.domain.user.model

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
class Profile (
    @Column(name = "nickname")
    var nickname: String
    )