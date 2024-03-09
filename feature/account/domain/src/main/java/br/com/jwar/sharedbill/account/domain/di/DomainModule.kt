package br.com.jwar.sharedbill.account.domain.di

import br.com.jwar.sharedbill.account.domain.repositories.UserRepository
import br.com.jwar.sharedbill.account.domain.services.AuthService
import br.com.jwar.sharedbill.account.domain.usecases.GetCurrentUserUseCase
import br.com.jwar.sharedbill.account.domain.usecases.GetCurrentUserUseCaseImpl
import br.com.jwar.sharedbill.account.domain.usecases.SignInFirebaseUseCase
import br.com.jwar.sharedbill.account.domain.usecases.SignInFirebaseUseCaseImpl
import br.com.jwar.sharedbill.account.domain.usecases.SignInUseCase
import br.com.jwar.sharedbill.account.domain.usecases.SignInUseCaseImpl
import br.com.jwar.sharedbill.account.domain.usecases.SignOutUseCase
import br.com.jwar.sharedbill.account.domain.usecases.SignOutUseCaseImpl
import br.com.jwar.sharedbill.account.domain.usecases.SignUpUseCase
import br.com.jwar.sharedbill.account.domain.usecases.SignUpUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {
    @Provides
    @Singleton
    fun provideGetAuthUserUseCase(
        userRepository: UserRepository
    ): GetCurrentUserUseCase = GetCurrentUserUseCaseImpl(userRepository)

    @Provides
    @Singleton
    fun provideSignInUseCase(
        authRepository: AuthService
    ): SignInUseCase = SignInUseCaseImpl(authRepository)

    @Provides
    @Singleton
    fun provideSignUpUseCase(
        authRepository: AuthService
    ): SignUpUseCase = SignUpUseCaseImpl(authRepository)

    @Provides
    @Singleton
    fun provideSignInFirebaseUseCase(
        authService: AuthService
    ): SignInFirebaseUseCase = SignInFirebaseUseCaseImpl(authService)

    @Provides
    @Singleton
    fun provideSignOutUseCase(
        authService: AuthService
    ): SignOutUseCase = SignOutUseCaseImpl(authService)
}
