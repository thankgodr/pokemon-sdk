package com.richard.pokemonsdk.`interface`

interface PokemonAccessResult<Success, Errors> {
    fun onStart()
    fun onNetworkrequest()
    fun onSuccess(success: Success)
    fun onError(error: Errors)
    fun complete()
}