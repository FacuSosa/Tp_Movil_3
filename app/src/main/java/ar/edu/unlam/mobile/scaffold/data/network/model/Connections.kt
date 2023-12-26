package ar.edu.unlam.mobile.scaffold.data.network.model

import com.google.gson.annotations.SerializedName

data class Connections(
    @SerializedName("groupAffiliation") val groupAffiliation: String = "NA",
    @SerializedName("relatives") val relatives: String = "NA"
)
