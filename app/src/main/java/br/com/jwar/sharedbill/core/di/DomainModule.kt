package br.com.jwar.sharedbill.core.di

import br.com.jwar.sharedbill.domain.repositories.GroupRepository
import br.com.jwar.sharedbill.domain.repositories.UserRepository
import br.com.jwar.sharedbill.domain.services.AuthService
import br.com.jwar.sharedbill.domain.usecases.*
import br.com.jwar.sharedbill.presentation.mappers.GroupToGroupUiModelMapper
import br.com.jwar.sharedbill.presentation.mappers.UserToUserUiModelMapper
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
        groupRepository: GroupRepository
    ): GetAllGroupsUseCase = GetAllGroupsUseCaseImpl(groupRepository)

    @Provides
    @Singleton
    fun providesCreateGroupUseCase(
        groupRepository: GroupRepository
    ): CreateGroupUseCase = CreateGroupUseCaseImpl(groupRepository)

    @Provides
    @Singleton
    fun providesSaveGroupUseCase(
        groupRepository: GroupRepository
    ): SaveGroupUseCase = SaveGroupUseCaseImpl(groupRepository)

    @Provides
    @Singleton
    fun providesGetGroupByIdUseCase(
        groupRepository: GroupRepository
    ): GetGroupByIdUseCase = GetGroupByIdUseCaseImpl(groupRepository)

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
        groupRepository: GroupRepository
    ): GroupJoinUseCase = GroupJoinUseCaseImpl(groupRepository)

    @Provides
    @Singleton
    fun providesSendPaymentUseCase(
        groupRepository: GroupRepository,
        getGroupByIdWithCurrentMemberUseCase: GetGroupByIdWithCurrentMemberUseCase
    ): SendPaymentUseCase = SendPaymentUseCaseImpl(groupRepository, getGroupByIdWithCurrentMemberUseCase)

    @Provides
    @Singleton
    fun providesGetGroupByIdWithCurrentMemberUseCase(
        userRepository: UserRepository,
        groupRepository: GroupRepository
    ): GetGroupByIdWithCurrentMemberUseCase = GetGroupByIdWithCurrentMemberUseCaseImpl(userRepository, groupRepository)

    @Provides
    @Singleton
    fun providesGetPaymentParamsUseCase(
        userToUserUiModelMapper: UserToUserUiModelMapper,
        groupToGroupUiModelMapper: GroupToGroupUiModelMapper
    ): GetPaymentParamsUseCase = GetPaymentParamsUseCaseImpl(groupToGroupUiModelMapper, userToUserUiModelMapper)
}