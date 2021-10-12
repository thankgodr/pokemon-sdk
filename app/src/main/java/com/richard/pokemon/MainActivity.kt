package com.richard.pokemon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.richard.pokemonsdk.model.ApiError
import com.richard.pokemonsdk.model.DescriptionResponse
import com.richard.pokemonsdk.model.PokemonRequest
import com.richard.pokemonsdk.pokinterface.PokemonAccessResult
import com.richard.pokemonsdk.wrapper.Pokemon

class MainActivity : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Pokemon.startUIforResult(this)
    }




}