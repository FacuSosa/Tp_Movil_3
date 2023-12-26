package ar.edu.unlam.mobile.scaffold.di

import ar.edu.unlam.mobile.scaffold.data.database.dao.DeckDao
import ar.edu.unlam.mobile.scaffold.data.database.dao.GuestDao
import ar.edu.unlam.mobile.scaffold.data.database.dao.HeroDao
import ar.edu.unlam.mobile.scaffold.data.database.guest.GuestRepository
import ar.edu.unlam.mobile.scaffold.data.network.HeroService
import ar.edu.unlam.mobile.scaffold.data.repository.deckrepository.DeckRepository
import ar.edu.unlam.mobile.scaffold.data.repository.deckrepository.IDeckRepository
import ar.edu.unlam.mobile.scaffold.data.repository.gamerepository.GameRepository
import ar.edu.unlam.mobile.scaffold.data.repository.gamerepository.IGameRepository
import ar.edu.unlam.mobile.scaffold.data.repository.herorepository.HeroRepository
import ar.edu.unlam.mobile.scaffold.data.repository.herorepository.IHeroRepository
import ar.edu.unlam.mobile.scaffold.data.repository.quizrepository.IQuizGameRepository
import ar.edu.unlam.mobile.scaffold.data.repository.quizrepository.QuizGameRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideHeroRepository(api: HeroService, db: HeroDao): IHeroRepository {
        return HeroRepository(api, db)
    }

    @Singleton
    @Provides
    fun provideGameRepository(repo: IHeroRepository): IGameRepository {
        return GameRepository(repo)
    }

    @Singleton
    @Provides
    fun provideQuizGameRepository(repo: IHeroRepository): IQuizGameRepository {
        return QuizGameRepository(repo)
    }

    @Singleton
    @Provides
    fun provideGuestRepository(guestBD: GuestDao): GuestRepository {
        return GuestRepository(guestBD)
    }

    @Singleton
    @Provides
    fun provideDeckRepository(deckDao: DeckDao, heroRepo: IHeroRepository): IDeckRepository {
        return DeckRepository(deckDao, heroRepo)
    }
}
