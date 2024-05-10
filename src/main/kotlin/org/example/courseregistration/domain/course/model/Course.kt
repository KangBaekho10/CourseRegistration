package org.example.courseregistration.domain.course.model

import jakarta.persistence.*
import org.example.courseregistration.domain.course.dto.CourseResponse
import org.example.courseregistration.domain.courseapplication.model.CourseApplication
import org.example.courseregistration.domain.lecture.model.Lecture

@Entity
@Table(name = "course")
class Course(
    @Column(name = "title", nullable = false)
    var title: String,

    @Column(name = "description")
    var description: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    var status: CourseStatus = CourseStatus.OPEN,

    @Column(name = "max_applicants", nullable = false)
    val maxApplicants: Int = 30,

    @Column(name = "num_applicants", nullable = false)
    var numApplicants: Int = 0,

//    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
//    @JoinColumn(name = "course_Id")
//    var lectures: MutableList<Lecture> = mutableListOf(),

    @OneToMany(mappedBy = "course", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    var courseApplications: MutableList<CourseApplication> = mutableListOf()
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    fun isFull(): Boolean {
        return numApplicants >= maxApplicants
    }

    fun isClosed(): Boolean {
        return status == CourseStatus.CLOSED
    }

    fun close() {
        status = CourseStatus.CLOSED
    }

    fun addApplicant() {
        numApplicants += 1
    }

//    fun addLecture(lecture: Lecture) {
//        lectures.add(lecture)
//    }
//
//    fun removeLecture(lecture: Lecture) {
//        lectures.remove(lecture)
//    }

    fun addCourseApplication(courseApplication: CourseApplication) {
        courseApplications.add(courseApplication)
    }

}

fun Course.toResponse(): CourseResponse {
    return CourseResponse(
        id = id!!,
        title = title,
        description = description,
        status = status.name,
        maxApplicants = maxApplicants,
        numApplicants = numApplicants
    )
}