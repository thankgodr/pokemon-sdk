package com.richard.pokemonsdk.model


data class shakespeareRespnse(var success : SuccessHolder, var contents : ContentHolder)

data class SuccessHolder(var total : Int)

data class ContentHolder(var translated : String, var text : String, var translation : String)