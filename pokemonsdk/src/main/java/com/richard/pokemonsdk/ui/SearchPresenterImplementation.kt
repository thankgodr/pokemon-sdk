package com.richard.pokemonsdk.ui

import android.util.Log.*
import com.google.gson.Gson
import com.richard.pokemonsdk.pokinterface.PokemonAccessResult
import com.richard.pokemonsdk.helpers.Variable
import com.richard.pokemonsdk.model.*
import com.richard.pokemonsdk.ui.adapters.SpritesAdapters
import com.richard.pokemonsdk.wrapper.Pokemon

class SearchPresenterImplementation(searchView: SearchUiContract.SearcUi) : SearchUiContract.SearchPresenter{
    var view: SearchUiContract.SearcUi = searchView
    val LOGTAG = "Okh"

    val request =PokemonRequest()
    val pokeMoneUI = PokemonDescriptionAndSprites(bitmap = ArrayList())

    //creating an observale to check when the job is complete
    val completedd = Variable(pokeMoneUI)


    override fun onDescriptionSubmit(text: String) {
        request.name = text
        //Write to view when Jobs are completed
        completedd.observable.subscribe {
            if (it.error) {
                view.displayError(it.errorMessage)
                view.hideProgress()
            } else {
                if (it.description.isNotEmpty()) {
                    view.buildView(it)
                    view.hideProgress()
                }
            }
        }



        Pokemon.getDescription(request, object :
            PokemonAccessResult<DescriptionResponse, ApiError> {
            override fun onPokStart() {
                view.showProgress();
            }

            override fun onNetworkrequest() {
                d(LOGTAG, "Getting Description")
            }

            override fun onSuccess(success: DescriptionResponse) {
                Pokemon.ShakespeareanDescription(
                    PokemonTranslateRequest(
                    success.flavor_text_entries!!.get(
                        0
                    ).flavor_text
                ), object :
                    PokemonAccessResult<shakespeareRespnse, ApiError> {
                    override fun onPokStart() {
                        d(LOGTAG, "Getting Translation")
                    }

                    override fun onNetworkrequest() {
                        d(LOGTAG, "Connecting to get Translation")
                    }

                    override fun onSuccess(success: shakespeareRespnse) {
                        pokeMoneUI.description = success.contents.translated
                        completedd.value = pokeMoneUI
                    }

                    override fun onError(error: ApiError) {
                        pokeMoneUI.error = true
                        updateError(error, pokeMoneUI)
                        completedd.value = pokeMoneUI
                    }

                    override fun complete() {
                        d(LOGTAG, "Getting Translation Completed")
                    }

                })
            }

            override fun onError(error: ApiError) {
                pokeMoneUI.error = true

                updateError(error, pokeMoneUI)

                completedd.value = pokeMoneUI
            }

            override fun complete() {
                i(LOGTAG, "Getting Disciprion Completed")
            }

        })





    }



    private fun updateError(error: ApiError, pokeMoneUI: PokemonDescriptionAndSprites) {
        if (error.info.isNullOrEmpty()) {
            pokeMoneUI.errorMessage = error.message
        } else {
            try{
                val apiError = Gson().fromJson<ApiError>(error.info, ApiError::class.java)
                pokeMoneUI.errorMessage = apiError.message
            }catch (e: Exception){
                pokeMoneUI.errorMessage = error.info!!
            }
        }
    }

    private fun downaloadImage(link: String?, pokeMoneUI: PokemonDescriptionAndSprites){
        if(!link.isNullOrEmpty()){
            pokeMoneUI.bitmap.add(link)
            
            //The code bellow is for mannual download but was passed to adapter and picaso handles the download
            //completedd.value = pokeMoneUI
            /* Pokemon.downloadSpritesImages(link!!, object : PokemonAccessResult<Bitmap, ApiError> {
                 override fun onStart() {
                     Log.d(LOGTAG, "Initiate downloading Image from ${link}")
                 }

                 override fun onNetworkrequest() {
                     Log.d(LOGTAG, "Network init for Image  download from ${link}")
                 }

                 override fun onSuccess(success: Bitmap) {
                     pokeMoneUI.bitmap.add(success)
                     completedd.value = pokeMoneUI
                 }

                 override fun onError(error: ApiError) {
                     pokeMoneUI.imgeDownloadFailed = true;
                     updateError(error, pokeMoneUI)
                     completedd.value = pokeMoneUI
                 }

                 override fun complete() {
                     Log.d(LOGTAG, "Network init for Image  download from ${link}")
                 }

             })

             */
        }
    }

    override fun onEnterPressed(text: String) {
        //Todo fix here
    }


    override fun onImageSubmit(text: String) {
        request.name = text
        //Write to view when Jobs are completed
        completedd.observable.subscribe{
            if(it.error){
                view.displayError(it.errorMessage)
                view.hideProgress()
            }
            else {
                if(it.bitmap.size > 0){
                    val adapters = SpritesAdapters(it.bitmap)
                    view.buildRecyclerView(adapters)
                    view.hideProgress()
                }else{
                    view.hideProgress();
                }
            }
        }

        Pokemon.getSprites(request, object : PokemonAccessResult<SpriteResponse, ApiError> {
            override fun onPokStart() {
                view.showProgress()
            }

            override fun onNetworkrequest() {
                d(LOGTAG, "Getting Sprites....")
            }

            override fun onSuccess(success: SpriteResponse) {
                val avaters = success.sprites!!
                downaloadImage(avaters.front_default, pokeMoneUI)
                downaloadImage(avaters.back_default, pokeMoneUI)
                downaloadImage(avaters.back_female, pokeMoneUI)
                downaloadImage(avaters.back_shiny, pokeMoneUI)
                downaloadImage(avaters.back_shiny_female, pokeMoneUI)
                downaloadImage(avaters.front_female, pokeMoneUI)
                downaloadImage(avaters.front_shiny_female, pokeMoneUI)
                downaloadImage(avaters.front_shiny, pokeMoneUI)
                completedd.value = pokeMoneUI
            }

            override fun onError(error: ApiError) {
                pokeMoneUI.error = true
                updateError(error, pokeMoneUI)
                completedd.value = pokeMoneUI
            }

            override fun complete() {
                d(LOGTAG, "Op getSprites completed")
            }

        })
    }

}