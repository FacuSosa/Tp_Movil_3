package ar.edu.unlam.mobile.scaffold.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ar.edu.unlam.mobile.scaffold.data.database.entities.GuestEntity
@Dao
interface GuestDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGuest(guestEntity: GuestEntity)

    @Query("SELECT * FROM usuario_table")
    suspend fun getGuests(): List<GuestEntity>
}