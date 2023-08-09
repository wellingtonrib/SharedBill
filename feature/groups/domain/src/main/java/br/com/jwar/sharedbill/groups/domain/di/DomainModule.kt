package br.com.jwar.sharedbill.groups.domain.di

import br.com.jwar.sharedbill.account.domain.repositories.UserRepository
import br.com.jwar.sharedbill.account.domain.usecases.*
import br.com.jwar.sharedbill.groups.domain.repositories.GroupRepository
import br.com.jwar.sharedbill.groups.domain.usecases.*
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
    ): AddMemberUseCase = AddMemberUseCaseImpl(groupRepository)

    @Provides
    @Singleton
    fun providesGroupRemoveMemberUseCase(
        groupRepository: GroupRepository
    ): RemoveMemberUseCase = RemoveMemberUseCaseImpl(groupRepository)

    @Provides
    @Singleton
    fun providesGroupJoinUseCase(
        groupRepository: GroupRepository,
        userRepository: UserRepository
    ): JoinGroupUseCase = JoinGroupUseCaseImpl(groupRepository, userRepository)

    @Provides
    @Singleton
    fun providesSendPaymentUseCase(
        groupRepository: GroupRepository
    ): SendPaymentUseCase = SendPaymentUseCaseImpl(groupRepository)

    @Provides
    @Singleton
    fun providesCreatePaymentUseCase(
        groupRepository: GroupRepository,
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
        removeMemberUseCase: RemoveMemberUseCase
    ): LeaveGroupUseCase = LeaveGroupUseCaseImpl(groupRepository, removeMemberUseCase)
}