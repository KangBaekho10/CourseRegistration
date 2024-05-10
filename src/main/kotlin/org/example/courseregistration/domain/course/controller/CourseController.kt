package org.example.courseregistration.domain.course.controller

import org.example.courseregistration.domain.course.dto.CourseResponse
import org.example.courseregistration.domain.course.dto.CreateCourseRequest
import org.example.courseregistration.domain.course.dto.UpdateCourseRequest
import org.example.courseregistration.domain.course.service.CourseService
import org.springframework.http.ResponseEntity
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RequestMapping("/courses")
@RestController
class CourseController(
    private val courseService: CourseService
) {

    @GetMapping
    fun getCourseList(): ResponseEntity<List<CourseResponse>> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(courseService.getAllCourseList())
    }

    @GetMapping("/{courseId}")
    fun getCourse(@PathVariable courseId: Long): ResponseEntity<CourseResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(courseService.getCourseById(courseId))
    }

    @PostMapping
    fun createCourse(@RequestBody createCourseRequest: CreateCourseRequest): ResponseEntity<CourseResponse> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(courseService.createCourse(createCourseRequest))
    }

    @PutMapping("/{courseId}")
    fun updateCourse(
        @PathVariable courseId: Long,
        @RequestBody request: UpdateCourseRequest
    ): ResponseEntity<CourseResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(courseService.updateCourse(courseId, request))
    }

    @DeleteMapping("/{courseId}")
    fun deleteCourse(
        @PathVariable courseId: Long
    ): ResponseEntity<Unit> {
        courseService.deleteCourse(courseId)
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build()
    }

}

