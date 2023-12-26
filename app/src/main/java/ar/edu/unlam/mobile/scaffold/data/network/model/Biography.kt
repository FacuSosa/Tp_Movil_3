package ar.edu.unlam.mobile.scaffold.data.network.model

import com.google.gson.annotations.SerializedName

data class Biography(
    @SerializedName("aliases") val aliases: List<String> = listOf("NA"),
    @SerializedName("alignment") val alignment: String = "NA",
    @SerializedName("alterEgos") val alterEgos: String = "NA",
    @SerializedName("firstAppearance") val firstAppearance: String = "NA",
    @SerializedName("fullName") val fullName: String = "NA",
    @SerializedName("placeOfBirth") val placeOfBirth: String = "NA",
    @SerializedName("publisher") val publisher: String = "NA"
)
