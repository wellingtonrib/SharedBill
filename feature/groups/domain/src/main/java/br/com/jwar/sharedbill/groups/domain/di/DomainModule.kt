@file:Suppress("WildcardImport", "NoWildcardImports")

package br.com.jwar.sharedbill.groups.domain.di

import br.com.jwar.sharedbill.account.domain.repositories.UserRepository
import br.com.jwar.sharedbill.core.utility.ExceptionHandler
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
        groupRepository: GroupRepository,
        exceptionHandler: ExceptionHandler,
    ): GetGroupsStreamUseCase = GetGroupsStreamUseCaseImpl(groupRepository, exceptionHandler)

    @Provides
    @Singleton
    fun providesCreateGroupUseCase(
        groupRepository: GroupRepository,
        userRepository: UserRepository,
        exceptionHandler: ExceptionHandler,
    ): CreateGroupUseCase = CreateGroupUseCaseImpl(groupRepository, userRepository, exceptionHandler)

    @Provides
    @Singleton
    fun providesSaveGroupUseCase(
        groupRepository: GroupRepository,
        exceptionHandler: ExceptionHandler,
    ): UpdateGroupUseCase = UpdateGroupUseCaseImpl(groupRepository, exceptionHandler)

    @Provides
    @Singleton
    fun providesGetGroupByIdUseCase(
        groupRepository: GroupRepository,
        exceptionHandler: ExceptionHandler,
    ): GetGroupByIdUseCase = GetGroupByIdUseCaseImpl(groupRepository, exceptionHandler)

    @Provides
    @Singleton
    fun providesGetGroupByIdStreamUseCase(
        groupRepository: GroupRepository,
        exceptionHandler: ExceptionHandler,
    ): GetGroupByIdStreamUseCase = GetGroupByIdStreamUseCaseImpl(groupRepository, exceptionHandler)

    @Provides
    @Singleton
    fun providesGroupAddMemberUseCase(
        groupRepository: GroupRepository,
        exceptionHandler: ExceptionHandler,
    ): AddMemberUseCase = AddMemberUseCaseImpl(groupRepository, exceptionHandler)

    @Provides
    @Singleton
    fun providesGroupRemoveMemberUseCase(
        groupRepository: GroupRepository
    ): RemoveMemberUseCase = RemoveMemberUseCaseImpl(groupRepository)

    @Provides
    @Singleton
    fun providesGroupJoinUseCase(
        groupRepository: GroupRepository,
        userRepository: UserRepository,
        exceptionHandler: ExceptionHandler,
    ): JoinGroupUseCase = JoinGroupUseCaseImpl(groupRepository, userRepository, exceptionHandler)

    @Provides
    @Singleton
    fun providesSendPaymentUseCase(
        groupRepository: GroupRepository,
        exceptionHandler: ExceptionHandler,
    ): SendPaymentUseCase = SendPaymentUseCaseImpl(groupRepository, exceptionHandler)

    @Provides
    @Singleton
    fun providesCreatePaymentUseCase(
        groupRepository: GroupRepository,
        exceptionHandler: ExceptionHandler,
    ): CreatePaymentUseCase = CreatePaymentUseCaseImpl(groupRepository, exceptionHandler)

    @Provides
    @Singleton
    fun providesDeleteGroupUseCase(
        groupRepository: GroupRepository,
        userRepository: UserRepository,
    ): DeleteGroupUseCase = DeleteGroupUseCaseImpl(groupRepository, userRepository)

    @Provides
    @Singleton
    fun providesGroupLeaveUseCase(
        groupRepository: GroupRepository,
        removeMemberUseCase: RemoveMemberUseCase
    ): LeaveGroupUseCase = LeaveGroupUseCaseImpl(groupRepository, removeMemberUseCase)

    @Provides
    @Singleton
    fun providesDeletePaymentUseCase(
        groupRepository: GroupRepository,
    ): DeletePaymentUseCase = DeletePaymentUseCaseImpl(groupRepository)
}
