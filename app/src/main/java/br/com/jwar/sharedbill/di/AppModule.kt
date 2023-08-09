package br.com.jwar.sharedbill.di

import android.app.Application
import android.content.Context
import br.com.jwar.sharedbill.core.utility.NetworkManager
import br.com.jwar.sharedbill.core.utility.StringProvider
import br.com.jwar.sharedbill.utility.AndroidStringProvider
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
    fun provideNetworkManager(
        context: Context
    ): NetworkManager = NetworkManagerImpl(context)

    @Provides
    fun provideStringProvider(
        context: Context
    ): StringProvider = AndroidStringProvider(context)
}