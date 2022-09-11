package br.com.jwar.sharedbill.presentation.di

import android.app.Application
import android.content.Context
import br.com.jwar.sharedbill.presentation.core.DispatcherProvider
import br.com.jwar.sharedbill.presentation.core.DispatcherProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideContext(
        app: Application
    ): Context = app.applicationContext

    @Provides
    fun provideDispatcher(): DispatcherProvider = DispatcherProviderImpl()
}