package br.com.jwar.sharedbill.core.di

import br.com.jwar.sharedbill.domain.repositories.GroupRepository
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

    @Provides
    @Singleton
    fun provideGetAllGroupsUseCase(
        groupRepository: GroupRepository
    ): GetGroupsStreamUseCase = GetGroupsStreamUseCaseImpl(groupRepository)

    @Provides
    @Singleton
    fun providesCreateGroupUseCase(
        groupRepository: GroupRepository,
        userRepository: UserRepository
    ): CreateGroupUseCase = CreateGroupUseCaseImpl(groupRepository, userRepository)

    @Provides
    @Singleton
    fun providesSaveGroupUseCase(
        groupRepository: GroupRepository
    ): UpdateGroupUseCase = UpdateGroupUseCaseImpl(groupRepository)

    @Provides
    @Singleton
    fun providesGetGroupByIdUseCase(
        groupRepository: GroupRepository
    ): GetGroupByIdUseCase = GetGroupByIdUseCaseImpl(groupRepository)

    @Provides
    @Singleton
    fun providesGetGroupByIdStreamUseCase(
        groupRepository: GroupRepository
    ): GetGroupByIdStreamUseCase = GetGroupByIdStreamUseCaseImpl(groupRepository)

    @Provides
    @Singleton
    fun providesGroupAddMemberUseCase(
        groupRepository: GroupRepository
    ): GroupAddMemberUseCase = GroupAddMemberUseCaseImpl(groupRepository)

    @Provides
    @Singleton
    fun providesGroupRemoveMemberUseCase(
        groupRepository: GroupRepository
    ): GroupRemoveMemberUseCase = GroupRemoveMemberUseCaseImpl(groupRepository)

    @Provides
    @Singleton
    fun providesGroupJoinUseCase(
        groupRepository: GroupRepository,
        userRepository: UserRepository
    ): GroupJoinUseCase = GroupJoinUseCaseImpl(groupRepository, userRepository)

    @Provides
    @Singleton
    fun providesSendPaymentUseCase(
        groupRepository: GroupRepository
    ): SendPaymentUseCase = SendPaymentUseCaseImpl(groupRepository)

    @Provides
    @Singleton
    fun providesCreatePaymentUseCase(
        groupRepository: GroupRepository
    ): CreatePaymentUseCase = CreatePaymentUseCaseImpl(groupRepository)

    @Provides
    @Singleton
    fun providesDeleteGroupUseCase(
        groupRepository: GroupRepository,
        userRepository: UserRepository
    ): DeleteGroupUseCase = DeleteGroupUseCaseImpl(groupRepository, userRepository)

    @Provides
    @Singleton
    fun providesGroupLeaveUseCase(
        groupRepository: GroupRepository,
        removeMemberUseCase: GroupRemoveMemberUseCase
    ): GroupLeaveUseCase = GroupLeaveUseCaseImpl(groupRepository, removeMemberUseCase)
}