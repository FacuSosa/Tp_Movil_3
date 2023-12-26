package ar.edu.unlam.mobile.scaffold.data.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import ar.edu.unlam.mobile.scaffold.data.database.dao.HeroDao
import ar.edu.unlam.mobile.scaffold.data.database.entities.AppearanceEntity
import ar.edu.unlam.mobile.scaffold.data.database.entities.BiographyEntity
import ar.edu.unlam.mobile.scaffold.data.database.entities.ConnectionsEntity
import ar.edu.unlam.mobile.scaffold.data.database.entities.HeroEntity
import ar.edu.unlam.mobile.scaffold.data.database.entities.ImageEntity
import ar.edu.unlam.mobile.scaffold.data.database.entities.PowerstatsEntity
import ar.edu.unlam.mobile.scaffold.data.database.entities.WorkEntity
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [31]) // robolectric no funciona con los últimos sdk
class HeroDataBaseTest {

    @get:Rule
    var instantTaskExecutor = InstantTaskExecutorRule()

    private lateinit var heroDataBase: HeroDataBase
    private lateinit var heroDao: HeroDao

    @Before
    fun setUp() {
        // val context = ApplicationProvider.getApplicationContext<Context>()
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        heroDataBase = Room
            .inMemoryDatabaseBuilder(
                context = context,
                klass = HeroDataBase::class.java
            ).allowMainThreadQueries()
            .build()
        heroDao = heroDataBase.getHeroDao()
    }

    @After
    fun tearDown() {
        heroDataBase.close()
    }

    @Test
    fun whenInsertAHeroEntityInDB_VerifyIfItWasInsertedInDB() = runTest {
        val expectedHero = HeroEntity(
            id = 1,
            powerstats = PowerstatsEntity(),
            connections = ConnectionsEntity(),
            appearance = AppearanceEntity(),
            biography = BiographyEntity(),
            image = ImageEntity(),
            name = "Mr. Test",
            work = WorkEntity()
        )

        heroDao.insertHero(hero = expectedHero)

        val allHeroes = heroDao.getAll()
        val hero = heroDao.getHero(expectedHero.id)

        assertThat(allHeroes).contains(expectedHero)
        assertThat(allHeroes).containsExactly(expectedHero)
        assertThat(allHeroes).hasSize(1)
        assertThat(allHeroes).isNotEmpty()
        assertThat(hero).isEqualTo(expectedHero)
    }

    private val heroEntityList = listOf(
        HeroEntity(
            id = 1,
            powerstats = PowerstatsEntity(),
            connections = ConnectionsEntity(),
            appearance = AppearanceEntity(),
            biography = BiographyEntity(),
            image = ImageEntity(),
            name = "Mr. Test 1",
            work = WorkEntity()
        ),
        HeroEntity(
            id = 2,
            powerstats = PowerstatsEntity(),
            connections = ConnectionsEntity(),
            appearance = AppearanceEntity(),
            biography = BiographyEntity(),
            image = ImageEntity(),
            name = "Mr. Test 2",
            work = WorkEntity()
        ),
        HeroEntity(
            id = 3,
            powerstats = PowerstatsEntity(),
            connections = ConnectionsEntity(),
            appearance = AppearanceEntity(),
            biography = BiographyEntity(),
            image = ImageEntity(),
            name = "Mr. Test 3",
            work = WorkEntity()
        ),
        HeroEntity(
            id = 4,
            powerstats = PowerstatsEntity(),
            connections = ConnectionsEntity(),
            appearance = AppearanceEntity(),
            biography = BiographyEntity(),
            image = ImageEntity(),
            name = "Mr. Test 4",
            work = WorkEntity()
        )
    )

    @Test
    fun whenInsertingAHeroEntityListInDB_VerifyIfTheListWasInserted() = runTest {
        heroDao.insertAll(heroEntityList)
        val heroList = heroDao.getAll()

        assertThat(heroList).hasSize(heroEntityList.size)
        assertThat(heroList).contains(heroEntityList[0])
        assertThat(heroList).contains(heroEntityList[1])
        assertThat(heroList).contains(heroEntityList[2])
        assertThat(heroList).contains(heroEntityList[3])
    }
}
