package ar.edu.unlam.mobile.scaffold.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class HeroQuantityUpdate(
    @ColumnInfo(name = "id", typeAffinity = ColumnInfo.INTEGER) val id: Int,
    @ColumnInfo(name = "quantity") val quantity: Int
)
