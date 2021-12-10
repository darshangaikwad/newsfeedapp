package com.darshangaikwad.mvvm_kotlin_with_koin.data.webservice

/**
 * Created by Darshan Gaikwad on 10,December,2021
 */
sealed class UseCaseResult<out T : Any> {
    class Success<out T : Any>(val data: T) : UseCaseResult<T>()
    class Error(val exception: Throwable) : UseCaseResult<Nothing>()
}