package com.example.mobiusdemo.stranger

import android.os.Parcelable
import android.util.Log
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import io.reactivex.rxjava3.core.Single
import kotlinx.parcelize.Parcelize
import retrofit2.Retrofit
import retrofit2.http.GET
import java.util.*
import javax.inject.Inject

class Repository @Inject constructor(
    private val retrofit: Retrofit
) {

    fun fetchMemes(): Single<List<Meme>> {
        return retrofit.create(MemesService::class.java)
            .getMemes()
            .doOnError { Log.e("", it.message, it) }
    }
}

interface MemesService {
    @GET("memes/5")
    fun getMemes(): Single<List<Meme>>
}

@Parcelize
@JsonClass(generateAdapter = true)
data class Meme(
    @Json(name = "_id") val id: String,
    val url: String,
    val createdAt: Date
) : Parcelable
