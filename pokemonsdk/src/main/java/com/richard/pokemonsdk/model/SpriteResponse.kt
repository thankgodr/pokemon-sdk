package com.richard.pokemonsdk.model

data class SpriteResponse(var id : Int = 0, var name : String = "", var sprites : SpritesNames? = null )

data class SpritesNames(var front_default : String, var front_shiny : String,
                        var front_female: String, var front_shiny_female : String,
                        var back_default: String, var back_shiny : String, var back_female : String,
                        var back_shiny_female : String)