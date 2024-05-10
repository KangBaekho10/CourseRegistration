package org.example.courseregistration.domain.course.service

import org.example.courseregistration.domain.course.dto.CourseResponse
import org.example.courseregistration.domain.course.dto.CreateCourseRequest
import org.example.courseregistration.domain.course.dto.UpdateCourseRequest
import org.example.courseregistration.domain.course.model.Course
import org.example.courseregistration.domain.course.model.CourseStatus
import org.example.courseregistration.domain.course.model.toResponse
import org.example.courseregistration.domain.course.repository.CourseRepository
import org.example.courseregistration.domain.courseapplication.dto.ApplyCourseRequest
import org.example.courseregistration.domain.courseapplication.dto.CourseApplicationResponse
import org.example.courseregistration.domain.courseapplication.dto.UpdateApplicationStatusRequest
import org.example.courseregistration.domain.courseapplication.model.CourseApplication
import org.example.courseregistration.domain.courseapplication.model.CourseApplicationStatus
import org.example.courseregistration.domain.courseapplication.model.toResponse
import org.example.courseregistration.domain.courseapplication.repository.CourseApplicationRepository
import org.example.courseregistration.domain.exception.ModelNotFoundException
import org.example.courseregistration.domain.lecture.dto.AddLectureRequest
import org.example.courseregistration.domain.lecture.dto.LectureResponse
import org.example.courseregistration.domain.lecture.dto.UpdateLectureRequest
import org.example.courseregistration.domain.lecture.model.Lecture
import org.example.courseregistration.domain.lecture.model.toResponse
import org.example.courseregistration.domain.lecture.repository.LectureRepository
import org.example.courseregistration.domain.user.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.IllegalStateException

@Service
class CourseServiceImpl(
    private val courseRepository: CourseRepository,
    private val lectureRepository: LectureRepository,
    private val courseApplicationRepository: CourseApplicationRepository,
    private val userRepository: UserRepository,
) : CourseService {
    override fun getAllCourseList(): List<CourseResponse> {
        return courseRepository.findAll().map { it.toResponse() }
    }

    override fun getCourseById(courseId: Long): CourseResponse {
        val course = courseRepository.findByIdOrNull(courseId) ?: throw ModelNotFoundException("Course", courseId)
        return course.toResponse()
    }

    @Transactional
    override fun createCourse(createCourseRequest: CreateCourseRequest): CourseResponse {
        return courseRepository.save(
            Course(
                title = createCourseRequest.title,
                description = createCourseRequest.description,
                status = CourseStatus.OPEN,
            )
        ).toResponse()
    }

    @Transactional
    override fun updateCourse(courseId: Long, updateCourseRequest: UpdateCourseRequest): CourseResponse {
        val course = courseRepository.findByIdOrNull(courseId) ?: throw ModelNotFoundException("Course", courseId)

        course.title = updateCourseRequest.title
        course.description = updateCourseRequest.description

        return courseRepository.save(course).toResponse()
    }

    @Transactional
    override fun deleteCourse(courseId: Long) {
        val course = courseRepository.findByIdOrNull(courseId) ?: throw ModelNotFoundException("Course", courseId)
        courseRepository.delete(course)
    }

    @Transactional
    override fun addLecture(courseId: Long, addLectureRequest: AddLectureRequest): LectureResponse {
        val course = courseRepository.findByIdOrNull(courseId) ?: throw ModelNotFoundException("Course", courseId)

        val lecture = Lecture(
            title = addLectureRequest.title,
            videoUrl = addLectureRequest.videoUrl,
            course = course
        )
//        course.addLecture(lecture)
//        courseRepository.save(course)
//        return lecture.toResponse()
        return lectureRepository.save(lecture).toResponse()
    }

    override fun getLecture(courseId: Long, lectureId: Long): LectureResponse {
//        val lecture = lectureRepository.findByCourseIdAndId(courseId, lectureId)
//            ?: throw ModelNotFoundException("Lecture", lectureId)
        val lecture = lectureRepository.findByIdOrNull(lectureId)
            ?: throw ModelNotFoundException("Lecture", lectureId)

        return lecture.toResponse()
    }

    override fun getLectureList(courseId: Long): List<LectureResponse> {
//        val course = courseRepository.findByIdOrNull(courseId) ?: throw ModelNotFoundException("Course", courseId)
//        return course.lectures.map { it.toResponse() }
        return lectureRepository.findAllByCourseId(courseId).map {it.toResponse()}
    }

    @Transactional
    override fun updateLecture(
        courseId: Long,
        lectureId: Long,
        request: UpdateLectureRequest
    ): LectureResponse {
        val lecture = lectureRepository.findByIdOrNull(lectureId)
            ?: throw ModelNotFoundException("Lecture", lectureId)

        val (title, videoUrl) = request
        lecture.title = title
        lecture.videoUrl = videoUrl

        return lectureRepository.save(lecture).toResponse()
    }

    @Transactional
    override fun removeLecture(courseId: Long, lectureId: Long) {
        val course = courseRepository.findByIdOrNull(courseId) ?: throw ModelNotFoundException("Course", courseId)
        val lecture = lectureRepository.findByIdOrNull(lectureId)
            ?: throw ModelNotFoundException("Lecture", lectureId)

//        course.removeLecture(lecture)
//
//        courseRepository.save(course)
        lectureRepository.delete(lecture)
    }

    @Transactional
    override fun applyCourse(courseId: Long, applyCourseRequest: ApplyCourseRequest): CourseApplicationResponse {
        val course = courseRepository.findByIdOrNull(courseId) ?: throw ModelNotFoundException("Course", courseId)
        val user = userRepository.findByIdOrNull(applyCourseRequest.userId)
            ?: throw ModelNotFoundException("User", applyCourseRequest.userId)

        if (course.isClosed()) {
            throw IllegalStateException("Course is already closed. courseId: $courseId")
        }

        if (courseApplicationRepository.existsByCourseIdAndUserId(courseId, applyCourseRequest.userId)) {
            throw IllegalStateException("Already applied. courseId: $courseId, userId: ${applyCourseRequest.userId}")
        }

        val courseApplication = CourseApplication(
            course = course,
            user = user,
        )
        course.addCourseApplication(courseApplication)
        courseRepository.save(course)

        return courseApplication.toResponse()
    }

    override fun getCourseApplication(courseId: Long, applicationId: Long): CourseApplicationResponse {
        val application = courseApplicationRepository.findByCourseIdAndId(courseId, applicationId)
            ?: throw ModelNotFoundException("CourseApplication", applicationId)

        return application.toResponse()
    }

    override fun getCourseApplicationList(courseId: Long): List<CourseApplicationResponse> {
        val course = courseRepository.findByIdOrNull(courseId) ?: throw ModelNotFoundException("Course", courseId)

        return course.courseApplications.map { it.toResponse() }
    }

    @Transactional
    override fun updateCourseApplicationStatus(
        courseId: Long,
        applicationId: Long,
        request: UpdateApplicationStatusRequest
    ): CourseApplicationResponse {
        val course = courseRepository.findByIdOrNull(courseId) ?: throw ModelNotFoundException("Course", courseId)
        val application = courseApplicationRepository.findByCourseIdAndId(courseId, applicationId)
            ?: throw ModelNotFoundException("CourseApplication", applicationId)

        if (application.isProceeded()) {
            throw IllegalStateException("Application is already proceeded. applicationId: $applicationId")
        }

        if (course.isClosed()) {
            throw IllegalStateException("Course is already closed. courseId: $courseId")
        }

        when (request.status) {
            CourseApplicationStatus.ACCEPTED.name -> {
                application.accept()
                course.addApplicant()
                if (course.isFull()) {
                    course.close()
                }
                courseRepository.save(course)
            }

            CourseApplicationStatus.REJECTED.name -> {
                application.reject()
            }

            else -> {
                throw IllegalArgumentException("Invalid status: ${request.status}")
            }
        }

        return courseApplicationRepository.save(application).toResponse()
    }
}
