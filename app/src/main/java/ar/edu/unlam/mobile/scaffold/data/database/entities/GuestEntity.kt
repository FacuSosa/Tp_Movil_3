package ar.edu.unlam.mobile.scaffold.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuario_table")
data class GuestEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val username: String?)

