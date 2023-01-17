package com.example.walletmanagerapplication.di

import android.app.Application
import androidx.room.Room
import com.example.walletmanagerapplication.data.RoomDb.AppDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideData(
        application: Application,
        callback: AppDataBase.Callback
    ) = Room.databaseBuilder(application, AppDataBase::class.java, "transaction_database")
        .fallbackToDestructiveMigration()
        .allowMainThreadQueries()
        .addCallback(callback)
        .build()

    @Provides
    fun providesTransactionDao(db:AppDataBase)=db.transactionDao()


    @ApplicationScope
    @Provides
    @Singleton
    fun provideApplicationScope()= CoroutineScope(SupervisorJob())

}
@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope
