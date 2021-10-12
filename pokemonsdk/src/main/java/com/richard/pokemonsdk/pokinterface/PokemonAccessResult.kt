package com.richard.pokemonsdk.pokinterface

interface PokemonAccessResult<Success, Errors> {
    fun onPokStart()
    fun onNetworkrequest()
    fun onSuccess(success: Success)
    fun onError(error: Errors)
    fun complete()
}