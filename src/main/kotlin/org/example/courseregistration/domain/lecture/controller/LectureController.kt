package org.example.courseregistration.domain.lecture.controller

import org.example.courseregistration.domain.course.service.CourseService
import org.example.courseregistration.domain.lecture.dto.AddLectureRequest
import org.example.courseregistration.domain.lecture.dto.LectureResponse
import org.example.courseregistration.domain.lecture.dto.UpdateLectureRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RequestMapping("/courses/{courseId}/lectures")
@RestController
class LectureController(
    private val courseService: CourseService
) {

    @GetMapping
    fun getLectureList(@PathVariable("courseId") courseId: Long): ResponseEntity<List<LectureResponse>>{
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(courseService.getLectureList(courseId))
    }

    @GetMapping("/{lectureId}")
    fun getLecture(@PathVariable courseId: Long, @PathVariable lectureId: Long): ResponseEntity<LectureResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(courseService.getLecture(courseId, lectureId))
    }

    @PostMapping
    fun addLecture(
        @PathVariable courseId: Long,
        @RequestBody addLectureRequest: AddLectureRequest
    ): ResponseEntity<LectureResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(courseService.addLecture(courseId, addLectureRequest))
    }

    @PutMapping("/{lectureId}")
    fun updateLecture(
        @PathVariable courseId: Long,
        @PathVariable lectureId: Long,
        @RequestBody updateLectureRequest: UpdateLectureRequest
    ): ResponseEntity<LectureResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(courseService.updateLecture(courseId, lectureId, updateLectureRequest))
    }

    @DeleteMapping("/{lectureId}")
    fun removeLecture(@PathVariable courseId: Long, @PathVariable lectureId: Long): ResponseEntity<Unit> {
        courseService.removeLecture(courseId, lectureId)
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build()
    }
}