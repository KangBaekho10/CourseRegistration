package org.example.courseregistration.domain.exception

data class ModelNotFoundException(
    val modelName: String, val id: Long):
    RuntimeException("Model $modelName not found with id $id")