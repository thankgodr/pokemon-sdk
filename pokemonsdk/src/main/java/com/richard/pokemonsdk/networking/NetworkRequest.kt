package com.richard.pokemonsdk.networking

import com.google.gson.Gson
import io.reactivex.rxjava3.core.Observable
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okio.IOException
import java.io.InputStream
import java.lang.Exception
import java.util.concurrent.Callable

class NetworkRequest<RETURN_CLASS>(val model : RETURN_CLASS){
    fun post(url: String, postbody: String): Observable<RETURN_CLASS> {
        val JSON = "application/json; charset=utf-8".toMediaTypeOrNull()
        val body = RequestBody.create(JSON, postbody)
        return Observable.fromCallable(object :
            @io.reactivex.rxjava3.annotations.NonNull Callable<RETURN_CLASS> {
            override fun call(): RETURN_CLASS {
                val request = Request.Builder()
                    .url(url)
                    .post(body)
                    .build()
                try{
                    val httpClient = OkHttpClient();
                    val response = httpClient.newCall(request).execute()
                    return if (response.isSuccessful) {
                        val json: String = response.body!!.string()
                        Gson().fromJson(json, model!!::class.java) as RETURN_CLASS
                    } else {
                        throw IOException(
                            "{'code': '${response.code}', 'message' : " +
                                    "'${response.message}', 'info' : '${response.body!!.string()}'}"
                        )
                    }
                }catch(e: Exception){
                    if(e is IOException){
                        throw IOException(
                            e.localizedMessage
                        )
                    }else{
                        throw IOException(
                            "{'code': 60000, 'message' : " +
                                    "'unknown Error', 'info' : '${e.localizedMessage}'}"
                        )
                    }
                }

            }

        })

    }
    fun get(url: String): Observable<RETURN_CLASS> {
        return Observable.fromCallable(object :
            @io.reactivex.rxjava3.annotations.NonNull Callable<RETURN_CLASS> {
            override fun call(): RETURN_CLASS {
                val request = Request.Builder()
                    .url(url)
                    .get()
                    .build()
                try{
                    val httpClient = OkHttpClient();
                    val response = httpClient.newCall(request).execute()
                    return if (response.isSuccessful) {
                        val json: String = response.body!!.string()
                        Gson().fromJson(json, model!!::class.java) as RETURN_CLASS
                    } else {
                        throw IOException(
                            "{'code': '${response.code}', 'message' : " +
                                    "'${response.message}', 'info' : '${response.body!!.string()}'}"
                        )
                    }
                }catch (e : Exception){
                    if(e is IOException){
                        if(e.localizedMessage.contains("message")){
                            throw IOException(
                                e.localizedMessage
                            )
                        }else{
                            throw IOException(
                                "{'code': 60000, 'message' : " +
                                        "'unknown Error', 'info' : '${e.localizedMessage}'}"
                            )
                        }
                    }else{
                        throw IOException(
                            "{'code': 60000, 'message' : " +
                                    "'unknown Error', 'info' : '${e.localizedMessage}'}"
                        )
                    }
                }

            }

        })

    }

    fun get(url: String, downloadStream : Boolean = true): Observable<InputStream> {
        return Observable.fromCallable(object :
            @io.reactivex.rxjava3.annotations.NonNull Callable<InputStream> {
            override fun call(): InputStream? {
                val request = Request.Builder()
                    .url(url)
                    .get()
                    .build()
                try{
                    val httpClient = OkHttpClient();
                    val response = httpClient.newCall(request).execute()
                    return if (response.isSuccessful) {
                        response.body!!.byteStream()
                    } else {
                        throw IOException(
                            "{'code': '${response.code}', 'message' : " +
                                    "'${response.message}', 'info' : '${response.body!!.string()}'}"
                        )!!
                    }
                }catch (e : Exception){
                    if(e is IOException){
                        throw IOException(
                            e.localizedMessage
                        )!!
                    }else{
                        throw IOException(
                            "{'code': 60000, 'message' : " +
                                    "'unknown Error', 'info' : '${e.localizedMessage}'}"
                        )!!
                    }
                }

            }

        })

    }
}