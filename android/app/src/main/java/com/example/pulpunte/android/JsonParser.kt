package com.example.pulpunte.android

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.Response

val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

inline fun <reified T> Response.get(): T {
    val json = body()!!.string()
    val adapter = moshi.adapter(T::class.java)
    return adapter.fromJson(json)!!
}