package org.example.courseregistration.domain.user.controller

import org.example.courseregistration.domain.user.dto.SignUpRequest
import org.example.courseregistration.domain.user.dto.UpdateUserProfile
import org.example.courseregistration.domain.user.dto.UserResponse
import org.example.courseregistration.domain.user.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestBody

@RestController
class UserController(
    private val userService: UserService
) {
    @PostMapping("/signUp")
    fun signUp(@RequestBody signUpRequest: SignUpRequest): ResponseEntity<UserResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.signUp(signUpRequest))
    }

    @PutMapping("/users/{userID}/profile")
    fun updateUserProfile(@PathVariable userID: Long,
                          @RequestBody updateUserProfileRequest: UpdateUserProfile
    ): ResponseEntity<UserResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.updateUserProfile(userID, updateUserProfileRequest))
    }
}