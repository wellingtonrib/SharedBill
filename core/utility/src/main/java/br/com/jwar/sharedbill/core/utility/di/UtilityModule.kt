package br.com.jwar.sharedbill.core.utility.di

import br.com.jwar.sharedbill.core.utility.ExceptionHandler
import br.com.jwar.sharedbill.core.utility.FirebaseExceptionHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UtilityModule {
    @Provides
    @Singleton
    fun provideExceptionHandler(): ExceptionHandler {
        return FirebaseExceptionHandler()
    }
}