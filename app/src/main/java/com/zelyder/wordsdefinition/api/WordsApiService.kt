package com.zelyder.wordsdefinition.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.zelyder.wordsdefinition.domain.models.WordDefinition
import com.zelyder.wordsdefinition.domain.models.WordProperty
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

private const val BASE_URL = "https://wordsapiv1.p.rapidapi.com/"
private const val KEY = "35a0be574dmshdd0221aad234d6fp12e94ejsn65baf5d0a5d7"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    //.addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface WordsApiService {
    @Headers("x-rapidapi-key: $KEY",
    "x-rapidapi-host:wordsapiv1.p.rapidapi.com")
    @GET("words/{word}")
    fun getInfoAboutWord(@Path("word") word : String): Deferred<List<WordProperty>>


    @Headers("x-rapidapi-key: $KEY",
        "x-rapidapi-host:wordsapiv1.p.rapidapi.com")
    @GET("words/{word}/definitions")
    fun getDefinitions(@Path("word") word: String): Deferred<WordDefinition>

}

object WordsApi {
    val retrofitService: WordsApiService by lazy {
        retrofit.create(WordsApiService::class.java)
    }
}