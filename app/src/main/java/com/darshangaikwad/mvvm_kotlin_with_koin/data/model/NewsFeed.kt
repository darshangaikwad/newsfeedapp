package com.darshangaikwad.mvvm_kotlin_with_koin.data.model

/**
 * Created by Darshan Gaikwad on 10,December,2021
 */
data class NewsFeed(
    val name: String,
    val category: String,
    val description: String,
    val image: String,
    var profileIcon: String,
    var likes_count: Int,
    val comments_count: Int,
    val campaign_name: String?, // may be null
    val campaign_collection: Int,
    val campaign_goal: Int,
    val campaign_end_date: Int,
    val patrons: List<Patrons>
)
