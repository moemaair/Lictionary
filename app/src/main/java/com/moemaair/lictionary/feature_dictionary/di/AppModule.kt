package com.moemaair.lictionary.feature_dictionary.di

import GetWordInfo
import GsonParser
import WordInfoRepoImpl
import android.app.Application
import androidx.room.Room
import com.google.gson.Gson
import com.moemaair.lictionary.feature_dictionary.data.local.Converters
import com.moemaair.lictionary.feature_dictionary.data.local.WordInfoDatabase
import com.moemaair.lictionary.feature_dictionary.data.remote.LictionaryApi
import com.moemaair.lictionary.feature_dictionary.domain.repository.WordInfoRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideGetWordInfoUseCase(repository: WordInfoRepo): GetWordInfo {
        return GetWordInfo(repository)
    }

    @Provides
    @Singleton
    fun provideWordInfoRepository(
        db: WordInfoDatabase,
        api: LictionaryApi
    ): WordInfoRepo {
        return WordInfoRepoImpl(api, db.dao)
    }

    @Provides
    @Singleton
    fun provideWordInfoDatabase(app: Application): WordInfoDatabase {
        return Room.databaseBuilder(
            app, WordInfoDatabase::class.java, "word_db"
        ).addTypeConverter(Converters(GsonParser(Gson())))
            .build()
    }

    @Provides
    @Singleton
    fun provideDictionaryApi(): LictionaryApi {
        return Retrofit.Builder()
            .baseUrl(LictionaryApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LictionaryApi::class.java)
    }

}