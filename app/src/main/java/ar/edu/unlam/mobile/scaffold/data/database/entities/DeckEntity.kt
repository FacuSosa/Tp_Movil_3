package ar.edu.unlam.mobile.scaffold.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ar.edu.unlam.mobile.scaffold.domain.model.DeckModel

@Entity(tableName = "deck_table")
data class DeckEntity(
    @ColumnInfo(name = "id", typeAffinity = ColumnInfo.INTEGER) @PrimaryKey(autoGenerate = true)
    val id: Int?,
    @ColumnInfo(name = "carta_1") val carta1: Int,
    @ColumnInfo(name = "carta_2") val carta2: Int,
    @ColumnInfo(name = "carta_3") val carta3: Int,
    @ColumnInfo(name = "carta_4") val carta4: Int,
    @ColumnInfo(name = "carta_5") val carta5: Int,
    @ColumnInfo(name = "carta_6") val carta6: Int
)

fun DeckModel.toEntity(): DeckEntity {
    return DeckEntity(
        id = null,
        carta1 = this.carta1.id,
        carta2 = this.carta2.id,
        carta3 = this.carta3.id,
        carta4 = this.carta4.id,
        carta5 = this.carta5.id,
        carta6 = this.carta6.id
    )
}
