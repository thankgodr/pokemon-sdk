package com.richard.pokemonsdk.wrapper

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.google.gson.Gson
import com.richard.pokemonsdk.pokinterface.PokemonAccessResult
import com.richard.pokemonsdk.helpers.Constant
import com.richard.pokemonsdk.model.*
import com.richard.pokemonsdk.networking.NetworkRequest
import com.richard.pokemonsdk.ui.SearchUi
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

object Pokemon {
    fun ShakespeareanDescription(shakespeareRequest: PokemonTranslateRequest,
                                 pokemonAccessResult: PokemonAccessResult<shakespeareRespnse, ApiError>
    ){
        pokemonAccessResult.onPokStart()
        //TOdo remove url to string
        val url = Constant.funtranslateURl
        val networkCall = NetworkRequest<shakespeareRespnse>(shakespeareRespnse(SuccessHolder(0), ContentHolder("","","")))
        pokemonAccessResult.onNetworkrequest()
        networkCall.post(url, Gson().toJson(shakespeareRequest)).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                pokemonAccessResult.onSuccess(it)
            },{
                pokemonAccessResult.onError(Gson().fromJson(it.message, ApiError::class.java))
            }, {
                pokemonAccessResult.complete()
            })
    }

    fun getDescription(request: PokemonRequest, pokemonAccessResult: PokemonAccessResult<DescriptionResponse, ApiError>){
        val url = Constant.specieUrl + request.name
        Log.i("okh", "URL is ${url}")
        val networkCall = NetworkRequest<DescriptionResponse>(DescriptionResponse(0,null))
        pokemonAccessResult.onPokStart()
        try{
            networkCall.get(url).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    pokemonAccessResult.onSuccess(it)
                }, {
                    it.printStackTrace()
                    pokemonAccessResult.onError(Gson().fromJson(it.message, ApiError::class.java))
                },{
                    pokemonAccessResult.complete()
                })
        }catch(e : Exception){
            pokemonAccessResult.onError(ApiError("Unknown Error", Constant.Unknown_Error, e.localizedMessage))
        }

    }

    fun getSprites(request: PokemonRequest, pokemonAccessResult: PokemonAccessResult<SpriteResponse, ApiError>){
        val url = Constant.pokemonBaseUrl + request.name
        val networkCall = NetworkRequest<SpriteResponse>(SpriteResponse())
        pokemonAccessResult.onPokStart()
        try {
            networkCall.get(url).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    pokemonAccessResult.onSuccess(it)
                },{
                    it.printStackTrace();
                    try{
                        pokemonAccessResult.onError(Gson().fromJson(it.message, ApiError::class.java))
                    }catch(e : Exception){
                        pokemonAccessResult.onError(ApiError(it.localizedMessage, Constant.Unknown_Error, it.message))
                    }
                },{
                    pokemonAccessResult.complete()
                })
        }catch(e : Exception){
            pokemonAccessResult.onError(ApiError("Unknown Error", Constant.Unknown_Error, e.localizedMessage))
        }
    }

    fun downloadSpritesImages(url : String, pokemonAccessResult: PokemonAccessResult<Bitmap, ApiError>){
        val networkCall = NetworkRequest<String>(String())
        pokemonAccessResult.onPokStart()
        try {
            networkCall.get(url,true).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    pokemonAccessResult.onSuccess(BitmapFactory.decodeStream(it))
                },{
                    it.printStackTrace()
                    try {
                        pokemonAccessResult.onError(Gson().fromJson(it.message, ApiError::class.java))
                    }catch (e : Exception){
                        pokemonAccessResult.onError(ApiError(e.localizedMessage, Constant.Unknown_Error, it.message))
                    }
                })
        }catch (e : Exception){
            pokemonAccessResult.onError(ApiError("Unknown Error", Constant.Unknown_Error, e.localizedMessage))
        }
    }

    fun startUIforResult(context : Activity){
        val intent  = Intent(context, SearchUi::class.java);
        context.startActivityForResult(intent, Constant.PokemonRequestCode)
    }

}