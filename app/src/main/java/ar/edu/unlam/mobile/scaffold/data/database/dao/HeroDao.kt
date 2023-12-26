package ar.edu.unlam.mobile.scaffold.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import ar.edu.unlam.mobile.scaffold.data.database.entities.HeroEntity
import ar.edu.unlam.mobile.scaffold.data.database.entities.HeroQuantityUpdate

@Dao
interface HeroDao {

    @Query("SELECT * FROM hero_table ORDER BY id")
    suspend fun getAll(): List<HeroEntity>

    @Query("SELECT * FROM hero_table WHERE id IS :idHero")
    suspend fun getHero(idHero: Int): HeroEntity?

    @Upsert
    suspend fun insertAll(heroList: List<HeroEntity>)

    @Upsert
    suspend fun insertHero(hero: HeroEntity)

    // documentación de como se realiza el update:
    // https://stackoverflow.com/a/59834309
    @Update(entity = HeroEntity::class)
    suspend fun updateQuantity(update: HeroQuantityUpdate)
}
