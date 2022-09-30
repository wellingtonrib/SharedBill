package br.com.jwar.sharedbill.presentation.di

import br.com.jwar.sharedbill.domain.repositories.GroupsRepository
import br.com.jwar.sharedbill.domain.repositories.UserRepository
import br.com.jwar.sharedbill.domain.services.AuthService
import br.com.jwar.sharedbill.domain.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Provides
    fun provideGetAuthUserUseCase(
        userRepository: UserRepository
    ): GetUserUseCase = GetUserUseCaseImpl(userRepository)

    @Provides
    fun provideSignInUseCase(
        authRepository: AuthService
    ): SignInUseCase = SignInUseCaseImpl(authRepository)

    @Provides
    fun provideSignUpUseCase(
        authRepository: AuthService
    ): SignUpUseCase = SignUpUseCaseImpl(authRepository)

    @Provides
    fun provideSignInFirebaseUseCase(
        authService: AuthService
    ): SignInFirebaseUseCase = SignInFirebaseUseCaseImpl(authService)

    @Provides
    fun provideSignOutUseCase(
        authService: AuthService
    ): SignOutUseCase = SignOutUseCaseImpl(authService)

    @Provides
    fun provideGetAllGroupsUseCase(
        groupsRepository: GroupsRepository
    ): GetAllGroupsUseCase = GetAllGroupsUseCaseImpl(groupsRepository)

    @Provides
    fun providesCreateGroupUseCase(
        groupsRepository: GroupsRepository
    ): CreateGroupUseCase = CreateGroupUseCaseImpl(groupsRepository)

    @Provides
    fun providesGetGroupByIdUseCase(
        groupsRepository: GroupsRepository
    ): GetGroupByIdUseCase = GetGroupByIdUseCaseImpl(groupsRepository)
}