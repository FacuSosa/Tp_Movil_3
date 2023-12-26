package ar.edu.unlam.mobile.scaffold.data.network.model

import com.google.gson.annotations.SerializedName

data class Powerstats(
    @SerializedName("combat") val combat: String = "001",
    @SerializedName("durability") val durability: String = "001",
    @SerializedName("intelligence") val intelligence: String = "001",
    @SerializedName("power") val power: String = "001",
    @SerializedName("speed") val speed: String = "001",
    @SerializedName("strength") val strength: String = "001"
)
