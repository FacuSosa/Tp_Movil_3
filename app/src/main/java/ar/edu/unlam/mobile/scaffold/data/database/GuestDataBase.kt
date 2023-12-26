package ar.edu.unlam.mobile.scaffold.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ar.edu.unlam.mobile.scaffold.data.database.dao.GuestDao
import ar.edu.unlam.mobile.scaffold.data.database.entities.GuestEntity

@Database(entities = [GuestEntity::class], version = 1)
abstract class GuestDataBase : RoomDatabase() {

    abstract fun getGuestDao(): GuestDao
}