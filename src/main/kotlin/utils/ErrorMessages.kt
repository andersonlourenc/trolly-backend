package com.lourenc.utils

object ErrorMessages {
    const val INVALID_EMAIL = "Invalid email format"
    const val EMAIL_AREADY_EXISTS = "Email already exists"
    const val INVALID_PASSWORD_8 = "Password must be 8 characters caracters"
    const val INVALID_PASSWORD_UPPERCASE = "Needs contain an uppercase letter"
    const val INVALID_PASSWORD_DIGIT = "Password must contain at least one digit"
    const val NAME_CANNOT_BE_BLACK = "Name cannot be blank"
    const val USER_NOT_FOUND = "User not found"
    const val INVALID_UUID = "Invalid UUID format"
    const val ID_REQUIRED = "ID is required"

    const val INTERNAL_SERVER_ERROR = "Internal server error"
    const val ERROR_FETCHING_USERS = "Error fetching users"
    const val ERROR_DELETING_USERS = "Error deleting users"
}