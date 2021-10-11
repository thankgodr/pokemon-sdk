package com.richard.pokemonsdk.ui

import android.content.Context
import com.richard.pokemonsdk.ui.adapters.SpritesAdapters

interface SearchUiContract {
    interface SearcUi{
        fun showProgress()
        fun hideProgress()
        fun buildView(pok : PokemonDescriptionAndSprites)
        fun getContext(): Context
        fun buildRecyclerView(adapter: SpritesAdapters)
        fun displayError(errorMessage : String);

    }

    interface  SearchPresenter{
        fun onDescriptionSubmit(text : String)
        fun onImageSubmit(text: String)
        fun onEnterPressed(text: String)
    }
}