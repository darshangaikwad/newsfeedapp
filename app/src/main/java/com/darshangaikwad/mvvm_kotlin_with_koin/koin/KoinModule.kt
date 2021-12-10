package com.darshangaikwad.mvvm_kotlin_with_koin.koin

import com.darshangaikwad.mvvm_kotlin_with_koin.ApplicationClass
import com.darshangaikwad.mvvm_kotlin_with_koin.data.repository.NewsFeedRepository
import com.darshangaikwad.mvvm_kotlin_with_koin.data.webservice.ServiceInterface
import com.darshangaikwad.mvvm_kotlin_with_koin.utils.HTTP_TIMEOUT
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


const val APP_BASE_URL = "http://protobak.com/"

val retrofitBuilder: Retrofit.Builder = Retrofit.Builder()
    .baseUrl(APP_BASE_URL)
    .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))

var appModules = module {
    single {
        createWebService<ServiceInterface>(
            okHttpClient = createHttpClient()
        )
    }
    single { ApplicationClass.mInstance.getPrefs() }
    factory {
        NewsFeedRepository(
            serviceInterface = get()
        )
    }
}

/** Returns a custom OkHttpClient instance with interceptor.
 *  Used for building Retrofit service.
 **/
fun createHttpClient(): OkHttpClient {
    val client = OkHttpClient.Builder()
    client.readTimeout(HTTP_TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(HTTP_TIMEOUT, TimeUnit.SECONDS)
        .connectTimeout(HTTP_TIMEOUT, TimeUnit.SECONDS)
    return client.addInterceptor {
        val original = it.request()
        val requestBuilder = original.newBuilder()
        requestBuilder.header("Content-Type", "application/json")
        val request = requestBuilder.method(original.method, original.body).build()
        return@addInterceptor it.proceed(request)
    }.build()
}

/****
 * Function to build our Retrofit service
 ***/
inline fun <reified T> createWebService(
    okHttpClient: OkHttpClient,
    baseUrl: String
): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
//            .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(okHttpClient)
        .build()
    return retrofit.create(T::class.java)
}

inline fun <reified T> createWebService(
    okHttpClient: OkHttpClient
): T {
    val retrofit = retrofitBuilder
//            .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(okHttpClient)
        .build()
    return retrofit.create(T::class.java)
}

