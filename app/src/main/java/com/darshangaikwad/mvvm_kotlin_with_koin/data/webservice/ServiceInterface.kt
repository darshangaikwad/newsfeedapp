package com.darshangaikwad.mvvm_kotlin_with_koin.data.webservice

import com.darshangaikwad.mvvm_kotlin_with_koin.data.model.NewsFeed
import retrofit2.http.GET

/**
 * Created by Darshan Gaikwad on 10,December,2021
 */
interface ServiceInterface {

    @GET("/protobak_star2/feed_data.json")
    suspend fun getNewsFeed(): List<NewsFeed>
}