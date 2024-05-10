package org.example.courseregistration.domain.course.repository

import org.example.courseregistration.domain.course.model.Course
import org.springframework.data.jpa.repository.JpaRepository

interface CourseRepository: JpaRepository<Course, Long> {
}