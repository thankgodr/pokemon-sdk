package com.richard.pokemonsdk.ui

data class PokemonDescriptionAndSprites(var name : String = "",
                                        var description :String = "", var bitmap: ArrayList<String>, var error : Boolean = false, var errorMessage : String= "", var imgeDownloadFailed : Boolean = false)
