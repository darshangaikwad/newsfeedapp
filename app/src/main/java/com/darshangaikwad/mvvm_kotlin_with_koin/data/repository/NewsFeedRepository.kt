package com.darshangaikwad.mvvm_kotlin_with_koin.data.repository

import com.darshangaikwad.mvvm_kotlin_with_koin.data.model.NewsFeed
import com.darshangaikwad.mvvm_kotlin_with_koin.data.webservice.ServiceInterface
import com.darshangaikwad.mvvm_kotlin_with_koin.data.webservice.UseCaseResult

/**
 * Created by Darshan Gaikwad on 10,December,2021
 */
class NewsFeedRepository(val serviceInterface: ServiceInterface) {

    suspend fun getNewsFeed(): UseCaseResult<List<NewsFeed>> {
        return try {
            UseCaseResult.Success(serviceInterface.getNewsFeed())
        } catch (ex: Exception) {
            UseCaseResult.Error(ex)
        }
    }

}