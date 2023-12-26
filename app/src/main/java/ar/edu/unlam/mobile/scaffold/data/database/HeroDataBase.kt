package ar.edu.unlam.mobile.scaffold.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ar.edu.unlam.mobile.scaffold.data.database.dao.HeroDao
import ar.edu.unlam.mobile.scaffold.data.database.entities.HeroEntity

@Database(entities = [HeroEntity::class], version = 1)
abstract class HeroDataBase : RoomDatabase() {

    abstract fun getHeroDao(): HeroDao
}
