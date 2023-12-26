package ar.edu.unlam.mobile.scaffold.data.network.model

import com.google.gson.annotations.SerializedName

data class Work(
    @SerializedName("base") val base: String = "NA",
    @SerializedName("occupation") val occupation: String = "NA"
)
