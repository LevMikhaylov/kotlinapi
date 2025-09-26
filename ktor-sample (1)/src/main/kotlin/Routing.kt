package com.example

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Application.configureRouting() {
    routing {
        // Корневой маршрут
        get("/") {
            call.respond(
                HttpStatusCode.OK,
                mapOf(
                    "message" to "User Management API",
                    "endpoints" to listOf(
                        "GET /users - Get all users",
                        "GET /users/{id} - Get user by ID",
                        "POST /users - Create new user",
                        "DELETE /users/{id} - Delete user"
                    )
                )
            )
        }

        // GET /users - получить всех пользователей
        get("/users") {
            val minAge = call.request.queryParameters["minAge"]?.toIntOrNull()
            val maxAge = call.request.queryParameters["maxAge"]?.toIntOrNull()
            val nameFilter = call.request.queryParameters["name"]

            val filteredUsers = users.filter { user ->
                (minAge == null || user.age >= minAge) &&
                        (maxAge == null || user.age <= maxAge) &&
                        (nameFilter == null || user.name.contains(nameFilter, ignoreCase = true))
            }

            call.respond(HttpStatusCode.OK, filteredUsers)
        }

        // GET /users/{id} - получить пользователя по ID
        get("/users/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: throw IllegalArgumentException("Invalid user ID")

            val user = users.find { it.id == id }
                ?: throw NotFoundException("User with ID $id not found")

            call.respond(HttpStatusCode.OK, user)
        }

        // POST /users - создать нового пользователя
        post("/users") {
            val user = call.receive<User>()

            // Валидация
            if (user.name.isBlank() || user.email.isBlank()) {
                throw IllegalArgumentException("Name and email are required")
            }

            if (users.any { it.email == user.email }) {
                throw ConflictException("User with this email already exists")
            }

            // Генерация ID
            val newId = (users.maxOfOrNull { it.id } ?: 0) + 1
            val newUser = user.copy(id = newId)
            users.add(newUser)

            call.respond(HttpStatusCode.Created, newUser)
        }

        // DELETE /users/{id} - удалить пользователя
        delete("/users/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: throw IllegalArgumentException("Invalid user ID")

            val user = users.find { it.id == id }
                ?: throw NotFoundException("User with ID $id not found")

            users.remove(user)
            call.respond(HttpStatusCode.NoContent)
        }
    }
}
class NotFoundException(message: String) : Exception(message)
class ConflictException(message: String) : Exception(message)
