package com.example

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int,
    val name: String,
    val email: String,
    val age: Int
)

@Serializable
data class ErrorResponse(
    val message: String,
    val code: Int
)

val users = mutableListOf(
    User(1, "Иван Иванов", "ivan@example.com", 25),
    User(2, "Мария Петрова", "maria@example.com", 30),
    User(3, "Алексей Сидоров", "alex@example.com", 28)
)