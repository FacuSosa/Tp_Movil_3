package ar.edu.unlam.mobile.scaffold.data.network.model

import com.google.gson.annotations.SerializedName

data class Appearance(
    @SerializedName("eyeColor") val eyeColor: String = "NA",
    @SerializedName("gender") val gender: String = "NA",
    @SerializedName("hairColor") val hairColor: String = "NA",
    @SerializedName("height") val height: List<String> = listOf("NA"),
    @SerializedName("race") val race: String = "NA",
    @SerializedName("weight") val weight: List<String> = listOf("NA")
)
