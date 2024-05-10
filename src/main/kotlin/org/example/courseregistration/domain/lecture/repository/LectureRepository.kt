package org.example.courseregistration.domain.lecture.repository

import org.example.courseregistration.domain.lecture.model.Lecture
import org.springframework.data.jpa.repository.JpaRepository

interface LectureRepository : JpaRepository<Lecture, Long> {

    // fun findByCourseIdAndId(courseId: Long, lectureId: Long): Lecture?
    fun findAllByCourseId(courseId: Long): List<Lecture>
}