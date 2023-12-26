package ar.edu.unlam.mobile.scaffold.data.network.model

import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("url") val url: String = "NA"
)
