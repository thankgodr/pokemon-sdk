package com.richard.pokemonsdk.model

data class DescriptionResponse(var id : Int = 0, var flavor_text_entries : List<FlavorEntries>? )

data class FlavorEntries(var  flavor_text : String = "", var language : FlavourLanguage? = null, var version : FlavourLanguage? = null)

data class FlavourLanguage(var name: String = "", var url : String = "")