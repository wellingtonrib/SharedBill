package br.com.jwar.sharedbill.di

import android.app.Application
import android.content.Context
import br.com.jwar.sharedbill.core.utility.extensions.NetworkManager
import br.com.jwar.sharedbill.utility.NetworkManagerImpl
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
    fun provideNetworkProvider(
        context: Context
    ): NetworkManager = NetworkManagerImpl(context)
}