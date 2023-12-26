package ar.edu.unlam.mobile.scaffold.di

import ar.edu.unlam.mobile.scaffold.data.network.IHeroApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://superheroapi.com/api/10160635333444116/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideHeroApiClient(retrofit: Retrofit): IHeroApiClient {
        return retrofit.create(IHeroApiClient::class.java)
    }
}
