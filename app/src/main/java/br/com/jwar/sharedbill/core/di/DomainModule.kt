package br.com.jwar.sharedbill.core.di

import br.com.jwar.sharedbill.domain.repositories.GroupsRepository
import br.com.jwar.sharedbill.domain.repositories.UserRepository
import br.com.jwar.sharedbill.domain.services.AuthService
import br.com.jwar.sharedbill.domain.usecases.*
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
    ): GetUserUseCase = GetUserUseCaseImpl(userRepository)

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

    @Provides
    @Singleton
    fun provideGetAllGroupsUseCase(
        groupsRepository: GroupsRepository
    ): GetAllGroupsUseCase = GetAllGroupsUseCaseImpl(groupsRepository)

    @Provides
    @Singleton
    fun providesCreateGroupUseCase(
        groupsRepository: GroupsRepository
    ): CreateGroupUseCase = CreateGroupUseCaseImpl(groupsRepository)

    @Provides
    @Singleton
    fun providesGetGroupByIdUseCase(
        groupsRepository: GroupsRepository
    ): GetGroupByIdUseCase = GetGroupByIdUseCaseImpl(groupsRepository)

    @Provides
    @Singleton
    fun providesGroupAddMemberUseCase(
        groupsRepository: GroupsRepository
    ): GroupAddMemberUseCase = GroupAddMemberUseCaseImpl(groupsRepository)

    @Provides
    @Singleton
    fun providesGroupJoinUseCase(
        groupsRepository: GroupsRepository
    ): GroupJoinUseCase = GroupJoinUseCaseImpl(groupsRepository)

    @Provides
    @Singleton
    fun providesSendPaymentUseCase(
        groupsRepository: GroupsRepository
    ): SendPaymentUseCase = SendPaymentUseCaseImpl(groupsRepository)
}