package app.translator.di

import android.content.Context
import app.translator.data.local.LanguagePreferencesRepositoryImpl
import app.translator.data.local.CoinLocalDataSource
import app.translator.data.local.LearnLocalDataSource
import app.translator.data.local.LearnProgressLocalDataSource
import app.translator.data.local.PhrasebookLocalDataSource
import app.translator.data.local.PreferencesDataStore
import app.translator.data.remote.LectoTranslateApiService
import app.translator.data.remote.LectoTranslateRemoteDataSource
import app.translator.data.remote.RetrofitProvider
import app.translator.data.repository.LearnRepositoryImpl
import app.translator.data.repository.CoinRepositoryImpl
import app.translator.data.repository.LearnProgressRepositoryImpl
import app.translator.data.repository.PhrasebookRepositoryImpl
import app.translator.data.repository.TranslationRepositoryImpl
import app.translator.domain.repository.LanguagePreferencesRepository
import app.translator.domain.repository.CoinRepository
import app.translator.domain.repository.LearnRepository
import app.translator.domain.repository.LearnProgressRepository
import app.translator.domain.repository.PhrasebookRepository
import app.translator.domain.repository.TranslationRepository
import app.translator.domain.usecase.GetLanguagePairUseCase
import app.translator.domain.usecase.ObserveCoinBalanceUseCase
import app.translator.domain.usecase.SpendCoinsUseCase
import app.translator.domain.usecase.GetLearnModulesUseCase
import app.translator.domain.usecase.ObserveUnlockedModulesUseCase
import app.translator.domain.usecase.GetConversationPhrasesUseCase
import app.translator.domain.usecase.GetExamQuestionsUseCase
import app.translator.domain.usecase.GetGrammarLessonsUseCase
import app.translator.domain.usecase.GetListenItemsUseCase
import app.translator.domain.usecase.GetPhrasebookCategoriesUseCase
import app.translator.domain.usecase.GetPhrasebookPhrasesUseCase
import app.translator.domain.usecase.GetVocabularyUseCase
import app.translator.domain.usecase.UnlockModuleUseCase
import app.translator.domain.usecase.SaveLanguagePairUseCase
import app.translator.domain.usecase.TranslateTextUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return RetrofitProvider.create()
    }

    @Provides
    @Singleton
    fun provideLectoTranslateApiService(retrofit: Retrofit): LectoTranslateApiService {
        return retrofit.create(LectoTranslateApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideLectoTranslateRemoteDataSource(
        apiService: LectoTranslateApiService
    ): LectoTranslateRemoteDataSource {
        return LectoTranslateRemoteDataSource(apiService)
    }

    @Provides
    @Singleton
    fun provideTranslationRepository(
        remoteDataSource: LectoTranslateRemoteDataSource
    ): TranslationRepository {
        return TranslationRepositoryImpl(remoteDataSource)
    }

    @Provides
    @Singleton
    fun provideLanguagePreferencesRepository(
        @ApplicationContext context: Context
    ): LanguagePreferencesRepository {
        return LanguagePreferencesRepositoryImpl(context)
    }

    @Provides
    @Singleton
    fun providePreferencesDataStore(
        @ApplicationContext context: Context
    ): PreferencesDataStore {
        return PreferencesDataStore(context)
    }

    @Provides
    @Singleton
    fun provideCoinLocalDataSource(
        dataStore: PreferencesDataStore
    ): CoinLocalDataSource {
        return CoinLocalDataSource(dataStore)
    }

    @Provides
    @Singleton
    fun provideLearnProgressLocalDataSource(
        dataStore: PreferencesDataStore
    ): LearnProgressLocalDataSource {
        return LearnProgressLocalDataSource(dataStore)
    }

    @Provides
    @Singleton
    fun provideLearnLocalDataSource(): LearnLocalDataSource {
        return LearnLocalDataSource()
    }

    @Provides
    @Singleton
    fun providePhrasebookLocalDataSource(): PhrasebookLocalDataSource {
        return PhrasebookLocalDataSource()
    }

    @Provides
    @Singleton
    fun provideLearnRepository(
        localDataSource: LearnLocalDataSource
    ): LearnRepository {
        return LearnRepositoryImpl(localDataSource)
    }

    @Provides
    @Singleton
    fun provideCoinRepository(
        localDataSource: CoinLocalDataSource
    ): CoinRepository {
        return CoinRepositoryImpl(localDataSource)
    }

    @Provides
    @Singleton
    fun provideLearnProgressRepository(
        localDataSource: LearnProgressLocalDataSource
    ): LearnProgressRepository {
        return LearnProgressRepositoryImpl(localDataSource)
    }

    @Provides
    @Singleton
    fun providePhrasebookRepository(
        localDataSource: PhrasebookLocalDataSource
    ): PhrasebookRepository {
        return PhrasebookRepositoryImpl(localDataSource)
    }

    @Provides
    fun provideTranslateTextUseCase(
        repository: TranslationRepository
    ): TranslateTextUseCase {
        return TranslateTextUseCase(repository)
    }

    @Provides
    fun provideGetLanguagePairUseCase(
        repository: LanguagePreferencesRepository
    ): GetLanguagePairUseCase {
        return GetLanguagePairUseCase(repository)
    }

    @Provides
    fun provideSaveLanguagePairUseCase(
        repository: LanguagePreferencesRepository
    ): SaveLanguagePairUseCase {
        return SaveLanguagePairUseCase(repository)
    }

    @Provides
    fun provideGetLearnModulesUseCase(
        repository: LearnRepository
    ): GetLearnModulesUseCase {
        return GetLearnModulesUseCase(repository)
    }

    @Provides
    fun provideObserveCoinBalanceUseCase(
        repository: CoinRepository
    ): ObserveCoinBalanceUseCase {
        return ObserveCoinBalanceUseCase(repository)
    }

    @Provides
    fun provideSpendCoinsUseCase(
        repository: CoinRepository
    ): SpendCoinsUseCase {
        return SpendCoinsUseCase(repository)
    }

    @Provides
    fun provideObserveUnlockedModulesUseCase(
        repository: LearnProgressRepository
    ): ObserveUnlockedModulesUseCase {
        return ObserveUnlockedModulesUseCase(repository)
    }

    @Provides
    fun provideUnlockModuleUseCase(
        repository: LearnProgressRepository
    ): UnlockModuleUseCase {
        return UnlockModuleUseCase(repository)
    }

    @Provides
    fun provideGetVocabularyUseCase(
        repository: LearnRepository
    ): GetVocabularyUseCase {
        return GetVocabularyUseCase(repository)
    }

    @Provides
    fun provideGetGrammarLessonsUseCase(
        repository: LearnRepository
    ): GetGrammarLessonsUseCase {
        return GetGrammarLessonsUseCase(repository)
    }

    @Provides
    fun provideGetExamQuestionsUseCase(
        repository: LearnRepository
    ): GetExamQuestionsUseCase {
        return GetExamQuestionsUseCase(repository)
    }

    @Provides
    fun provideGetListenItemsUseCase(
        repository: LearnRepository
    ): GetListenItemsUseCase {
        return GetListenItemsUseCase(repository)
    }

    @Provides
    fun provideGetConversationPhrasesUseCase(
        repository: LearnRepository
    ): GetConversationPhrasesUseCase {
        return GetConversationPhrasesUseCase(repository)
    }

    @Provides
    fun provideGetPhrasebookCategoriesUseCase(
        repository: PhrasebookRepository
    ): GetPhrasebookCategoriesUseCase {
        return GetPhrasebookCategoriesUseCase(repository)
    }

    @Provides
    fun provideGetPhrasebookPhrasesUseCase(
        repository: PhrasebookRepository
    ): GetPhrasebookPhrasesUseCase {
        return GetPhrasebookPhrasesUseCase(repository)
    }
}
