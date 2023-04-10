package com.moviestreaming.utils

import com.google.gson.Gson
import com.moviestreaming.data.model.ErrorResponse
import retrofit2.HttpException

fun parsError(e: Exception): ErrorResponse =
    when (e) {
        is HttpException -> {
            try {
                Gson().fromJson(e.response()?.errorBody()?.string(), ErrorResponse::class.java)
            } catch (exp: Exception) {
                exp.printStackTrace()
                ErrorResponse(-1, e.message(), false)
            }
        }
        else -> {
            ErrorResponse(-1, statusMessage = "Server problems, please try later!", success = false)
        }
    }