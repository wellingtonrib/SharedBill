package br.com.jwar.sharedbill.di

import android.content.Context
import br.com.jwar.sharedbill.BuildConfig
import br.com.jwar.sharedbill.core.utility.ExceptionHandler
import br.com.jwar.sharedbill.core.utility.FirebaseExceptionHandler
import br.com.jwar.sharedbill.core.utility.NetworkManager
import br.com.jwar.sharedbill.core.utility.StringProvider
import br.com.jwar.sharedbill.utility.AndroidStringProvider
import br.com.jwar.sharedbill.utility.NetworkManagerImpl
import com.google.firebase.appcheck.AppCheckProviderFactory
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideNetworkManager(
        @ApplicationContext context: Context
    ): NetworkManager = NetworkManagerImpl(context)

    @Provides
    fun provideStringProvider(
        @ApplicationContext context: Context
    ): StringProvider = AndroidStringProvider(context)

    @Provides
    @Singleton
    fun provideExceptionHandler(): ExceptionHandler {
        return FirebaseExceptionHandler()
    }

    @Provides
    @Singleton
    fun provideAppCheckFactory(): AppCheckProviderFactory {
        return if (BuildConfig.DEBUG) {
            DebugAppCheckProviderFactory.getInstance()
        } else {
            PlayIntegrityAppCheckProviderFactory.getInstance()
        }
    }
}
