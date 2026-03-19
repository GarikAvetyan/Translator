package app.translator.di

import app.translator.data.remote.LectoTranslateApiService
import app.translator.data.remote.RetrofitProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

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
}
