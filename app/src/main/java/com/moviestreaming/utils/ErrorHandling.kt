package com.moviestreaming.utils

import com.google.gson.Gson
import com.moviestreaming.data.model.ErrorResponse
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun parsError(e: Throwable): ErrorResponse =
    when (e) {
        is HttpException -> {
            try {
                val errorBody = e.response()?.errorBody()?.string()
                if (!errorBody.isNullOrEmpty()) {
                    Gson().fromJson(errorBody, ErrorResponse::class.java)
                } else {
                    createHttpErrorResponse(e.code())
                }
            } catch (exp: Exception) {
                exp.printStackTrace()
                ErrorResponse(-1, "Server error: ${e.message() ?: "Unknown"}", false)
            }
        }
        is UnknownHostException -> {
            ErrorResponse(-2, "No internet connection. Please check your network settings.", false)
        }
        is SocketTimeoutException -> {
            ErrorResponse(-3, "Connection timed out. Please try again later.", false)
        }
        is IOException -> {
            ErrorResponse(-4, "Network error: ${e.message ?: "Unknown"}", false)
        }
        else -> {
            ErrorResponse(-5, "An unexpected error occurred: ${e.message ?: "Unknown error"}", false)
        }
    }

private fun createHttpErrorResponse(code: Int): ErrorResponse {
    val message = when (code) {
        401 -> "Unauthorized. Please login again."
        403 -> "Access denied. You don't have permission to access this resource."
        404 -> "The requested resource was not found."
        429 -> "Too many requests. Please try again later."
        500 -> "Server error. Please try again later."
        502 -> "Bad gateway. Please try again later."
        503 -> "Service unavailable. Please try again later."
        504 -> "Gateway timeout. Please try again later."
        else -> "Error code: $code"
    }
    return ErrorResponse(code, message, false)
}