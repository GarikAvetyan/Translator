package app.translator.di

import app.translator.data.repository.LanguagePreferencesRepositoryImpl
import app.translator.data.repository.CoinRepositoryImpl
import app.translator.data.repository.LearnProgressRepositoryImpl
import app.translator.data.repository.LearnRepositoryImpl
import app.translator.data.repository.PhrasebookRepositoryImpl
import app.translator.data.repository.TranslationRepositoryImpl
import app.translator.domain.repository.CoinRepository
import app.translator.domain.repository.LanguagePreferencesRepository
import app.translator.domain.repository.LearnProgressRepository
import app.translator.domain.repository.LearnRepository
import app.translator.domain.repository.PhrasebookRepository
import app.translator.domain.repository.TranslationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindTranslationRepository(
        impl: TranslationRepositoryImpl
    ): TranslationRepository

    @Binds
    @Singleton
    abstract fun bindLanguagePreferencesRepository(
        impl: LanguagePreferencesRepositoryImpl
    ): LanguagePreferencesRepository

    @Binds
    @Singleton
    abstract fun bindCoinRepository(
        impl: CoinRepositoryImpl
    ): CoinRepository

    @Binds
    @Singleton
    abstract fun bindLearnRepository(
        impl: LearnRepositoryImpl
    ): LearnRepository

    @Binds
    @Singleton
    abstract fun bindLearnProgressRepository(
        impl: LearnProgressRepositoryImpl
    ): LearnProgressRepository

    @Binds
    @Singleton
    abstract fun bindPhrasebookRepository(
        impl: PhrasebookRepositoryImpl
    ): PhrasebookRepository
}
