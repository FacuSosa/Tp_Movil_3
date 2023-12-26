package ar.edu.unlam.mobile.scaffold.data.repository.herorepository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import ar.edu.unlam.mobile.scaffold.data.database.dao.HeroDao
import ar.edu.unlam.mobile.scaffold.data.database.entities.HeroEntity
import ar.edu.unlam.mobile.scaffold.data.network.HeroService
import ar.edu.unlam.mobile.scaffold.data.network.model.HeroApiModel
import ar.edu.unlam.mobile.scaffold.domain.model.HeroModel
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit4.MockKRule
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Rule
import org.junit.Test

class HeroRepositoryTest {

    /*
        JUnit 4 exposes a rule-based API to allow for some automation following the test lifecycle.
        MockK includes a rule which uses this to set up and tear down your mocks without needing to
        manually call MockKAnnotations.init(this).
     */
    @get:Rule
    val mockkRule = MockKRule(this)

    /*
        Esta regla es para que se ejecute cada línea de código de forma secuencial.
        Nos sirve en casos en donde se necesite el uso de livedata, flow, suspend functions, etc.
     */
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    /*
        @relaxedmockk
        se pueden mockear manualmente los métodos que necesitamos y el resto se genera
        automáticamente.

        @mockk
        debemos generar manualmente una respuesta para todos los métodos.
     */
    @RelaxedMockK
    lateinit var api: HeroService

    @RelaxedMockK
    lateinit var db: HeroDao

    @After
    fun afterTests() {
        unmockkAll()
    }

    @Test
    fun getHeroFromApiWhenRoomDoesntHaveIt() = runTest {
        val apiHero = HeroApiModel(id = "1", name = "Mr. Test")
        val expectedHero = HeroModel(id = 1, name = "Mr. Test")
        coEvery { api.getHero(1) } returns apiHero
        coEvery { db.getHero(1) } returns null

        val repo = HeroRepository(api = api, dataBase = db)
        val hero = repo.getHero(1)

        assertThat(hero).isEqualTo(expectedHero)
    }

    @Test
    fun getHeroFromDatabaseWhenItHasIt() = runTest {
        val heroEntity = HeroEntity(id = 1, name = "Mr. Test")
        val expectedHero = HeroModel(id = 1, name = "Mr. Test")
        coEvery { api.getHero(1) } returns HeroApiModel(id = "2")
        coEvery { db.getHero(1) } returns heroEntity

        val repo = HeroRepository(api, db)
        val hero = repo.getHero(1)

        assertThat(hero).isEqualTo(expectedHero)
    }

    @Test(expected = HeroRepositoryException::class)
    fun whenProvidingHeroIdLowerThanOneThrowHeroRepositoryException() = runTest {
        HeroRepository(api, db).getHero(0)
    }

    @Test(expected = HeroRepositoryException::class)
    fun whenProvidingHeroIdHigherThan731ThrowHeroRepositoryException() = runTest {
        HeroRepository(api, db).getHero(732)
    }

    @Test
    fun whenDatabaseHas731EntriesReturnListDirectlyWithSize731() = runTest {
        val expectedSize = 731
        val heroEntityList: MutableList<HeroEntity> = mutableListOf()
        for (i in 1..expectedSize) {
            heroEntityList.add(HeroEntity(id = i, name = "$i test"))
        }
        coEvery { db.getAll() } returns heroEntityList
        val listSize = HeroRepository(api, db).getAllHero().size
        assertThat(listSize).isEqualTo(expectedSize)
    }

    @Test
    fun whenDatabaseHasZeroEntriesGetEverythingFromApi() = runTest {
        coEvery { db.getAll() } returns emptyList()
        for (i in 1..731) {
            coEvery { api.getHero(i) } returns HeroApiModel(id = i.toString(), name = "Test $i")
        }
        val heroList = HeroRepository(api, db).getAllHero()
        for (i in 1..731) {
            val hero = heroList[i - 1]
            val name = hero.name
            val id = hero.id
            assertThat(name).isEqualTo("Test $i")
            assertThat(id).isEqualTo(i)
        }
    }

    @Test
    fun whenProvidingASizeToGetRandomPlayerDeckReturnAHeroListOfAskedSize() = runTest {
        val expectedSize = 4
        coEvery { db.getAll() } returns emptyList()
        for (i in 1..731) {
            coEvery { api.getHero(i) } returns HeroApiModel(id = i.toString(), name = "Test $i")
        }
        val heroDeck = HeroRepository(api, db).getRandomPlayerDeck(expectedSize)
        assertThat(heroDeck.size).isEqualTo(expectedSize)
    }

    @Test
    fun whenPreloadingHeroCacheEmitValuesBetweenZeroAndOne() = runTest {
        coEvery { db.getAll() } returns emptyList()
        for (i in 1..731) {
            coEvery { api.getHero(i) } returns HeroApiModel(id = i.toString(), name = "Test $i")
        }
        val emissionList = HeroRepository(api, db).preloadHeroCache().toList()
        val iterator = emissionList.iterator()
        assertThat(emissionList[0]).isWithin(1.0E-5F).of(0f)
        assertThat(emissionList.last()).isWithin(1.0E-5F).of(1f)
        while (iterator.hasNext()) {
            val value = iterator.next()
            assertThat(value).isAtLeast(0f)
            assertThat(value).isAtMost(1f)
        }
    }
}
