package ar.edu.unlam.mobile.scaffold.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ar.edu.unlam.mobile.scaffold.data.database.dao.DeckDao
import ar.edu.unlam.mobile.scaffold.data.database.entities.DeckEntity

@Database(entities = [DeckEntity::class], version = 1)
abstract class DeckDatabase : RoomDatabase() {
    abstract fun getDeckDao(): DeckDao
}
