package org.example.courseregistration.domain.course.service

import org.example.courseregistration.domain.course.dto.CourseResponse
import org.example.courseregistration.domain.course.dto.CreateCourseRequest
import org.example.courseregistration.domain.course.dto.UpdateCourseRequest
import org.example.courseregistration.domain.courseapplication.dto.ApplyCourseRequest
import org.example.courseregistration.domain.courseapplication.dto.CourseApplicationResponse
import org.example.courseregistration.domain.courseapplication.dto.UpdateApplicationStatusRequest
import org.example.courseregistration.domain.lecture.dto.AddLectureRequest
import org.example.courseregistration.domain.lecture.dto.LectureResponse
import org.example.courseregistration.domain.lecture.dto.UpdateLectureRequest

interface CourseService {

    fun getAllCourseList(): List<CourseResponse>

    fun getCourseById(courseId: Long): CourseResponse

    fun createCourse(createCourseRequest: CreateCourseRequest): CourseResponse

    fun updateCourse(courseId: Long, updateCourseRequest: UpdateCourseRequest): CourseResponse

    fun deleteCourse(courseId: Long)

    fun getLectureList(courseId: Long): List<LectureResponse>

    fun getLecture(courseId: Long, lectureId: Long): LectureResponse

    fun addLecture(courseId: Long, addLectureRequest: AddLectureRequest): LectureResponse

    fun updateLecture(
        courseId: Long,
        lectureId: Long,
        request: UpdateLectureRequest
    ): LectureResponse

    fun removeLecture(courseId: Long, lectureId: Long)

    fun applyCourse(courseId: Long, applyCourseRequest: ApplyCourseRequest): CourseApplicationResponse

    fun getCourseApplication(
        courseId: Long,
        applicationId: Long
    ): CourseApplicationResponse

    fun getCourseApplicationList(courseId: Long): List<CourseApplicationResponse>

    fun updateCourseApplicationStatus(
        courseId: Long,
        applicationId: Long,
        request: UpdateApplicationStatusRequest
    ): CourseApplicationResponse
}