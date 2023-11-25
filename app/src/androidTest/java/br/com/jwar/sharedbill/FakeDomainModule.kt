package br.com.jwar.sharedbill

import br.com.jwar.sharedbill.groups.domain.di.DomainModule
import br.com.jwar.sharedbill.groups.domain.model.Group
import br.com.jwar.sharedbill.groups.domain.model.Payment
import br.com.jwar.sharedbill.groups.domain.model.PaymentType
import br.com.jwar.sharedbill.groups.domain.usecases.*
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DomainModule::class]
)
abstract class FakeDomainModule {

    class FakeGetGroupsStreamUseCase @Inject constructor(): GetGroupsStreamUseCase {
        override suspend fun invoke(): Flow<Result<List<Group>>> {
            return flowOf(Result.success(listOf(Group())))
        }
    }

    class FakeCreateGroupUseCase @Inject constructor(): CreateGroupUseCase {
        override suspend fun invoke(title: String): Result<String> {
            return Result.success(Group().id)
        }
    }

    class FakeSaveGroupUseCase @Inject constructor(): UpdateGroupUseCase {
        override suspend fun invoke(groupId: String, title: String): Result<Unit> {
            return Result.success(Unit)
        }
    }

    class FakeGetGroupByIdUseCase @Inject constructor(): GetGroupByIdUseCase {
        override suspend fun invoke(groupId: String, refresh: Boolean): Result<Group> {
            return Result.success(Group())
        }
    }

    class FakeGetGroupByIdStreamUseCase @Inject constructor(): GetGroupByIdStreamUseCase {
        override suspend fun invoke(groupId: String): Flow<Result<Group>> {
            return flowOf(Result.success(Group()))
        }
    }

    class FakeAddMemberUseCase @Inject constructor(): AddMemberUseCase {
        override suspend fun invoke(userName: String, groupId: String): Result<String> {
            return Result.success(UUID.randomUUID().toString())
        }
    }

    class FakeRemoveMemberUseCase @Inject constructor(): RemoveMemberUseCase {
        override suspend fun invoke(userId: String, groupId: String): Result<Unit> {
            return Result.success(Unit)
        }
    }

    class FakeJoinGroupUseCase @Inject constructor(): JoinGroupUseCase {
        override suspend fun invoke(inviteCode: String): Result<String> {
            return Result.success(UUID.randomUUID().toString())
        }
    }

    class FakeSendPaymentUseCase @Inject constructor(): SendPaymentUseCase {
        override suspend fun invoke(payment: Payment): Result<Unit> {
            return Result.success(Unit)
        }
    }

    class FakeCreatePaymentUseCase @Inject constructor(): CreatePaymentUseCase {
        override suspend fun invoke(
            description: String,
            value: String,
            dateTime: Long,
            paidById: String,
            paidToIds: List<String>,
            groupId: String,
            paymentType: PaymentType
        ): Result<Payment> {
            return Result.success(Payment())
        }
    }

    class FakeDeleteGroupUseCase @Inject constructor(): DeleteGroupUseCase {
        override suspend fun invoke(groupId: String): Result<Unit> {
            return Result.success(Unit)
        }
    }

    class FakeLeaveGroupUseCase @Inject constructor(): LeaveGroupUseCase {
        override suspend fun invoke(groupId: String): Result<Unit> {
            return Result.success(Unit)
        }
    }


    @Binds
    @Singleton
    abstract fun bindGetAllGroupsUseCase(
        fake: FakeGetGroupsStreamUseCase
    ): GetGroupsStreamUseCase

    @Binds
    @Singleton
    abstract fun bindCreateGroupUseCase(
        fake: FakeCreateGroupUseCase
    ): CreateGroupUseCase

    @Binds
    @Singleton
    abstract fun bindSaveGroupUseCase(
        fake: FakeSaveGroupUseCase
    ): UpdateGroupUseCase

    @Binds
    @Singleton
    abstract fun bindGetGroupByIdUseCase(
        fake: FakeGetGroupByIdUseCase
    ): GetGroupByIdUseCase

    @Binds
    @Singleton
    abstract fun bindGetGroupByIdStreamUseCase(
        fake: FakeGetGroupByIdStreamUseCase
    ): GetGroupByIdStreamUseCase

    @Binds
    @Singleton
    abstract fun bindGroupAddMemberUseCase(
        fake: FakeAddMemberUseCase
    ): AddMemberUseCase

    @Binds
    @Singleton
    abstract fun bindGroupRemoveMemberUseCase(
        fake: FakeRemoveMemberUseCase
    ): RemoveMemberUseCase

    @Binds
    @Singleton
    abstract fun bindGroupJoinUseCase(
        fake: FakeJoinGroupUseCase
    ): JoinGroupUseCase

    @Binds
    @Singleton
    abstract fun bindSendPaymentUseCase(
        fake: FakeSendPaymentUseCase
    ): SendPaymentUseCase

    @Binds
    @Singleton
    abstract fun bindCreatePaymentUseCase(
        fake: FakeCreatePaymentUseCase
    ): CreatePaymentUseCase

    @Binds
    @Singleton
    abstract fun bindDeleteGroupUseCase(
        fake: FakeDeleteGroupUseCase
    ): DeleteGroupUseCase

    @Binds
    @Singleton
    abstract fun bindGroupLeaveUseCase(
        fake: FakeLeaveGroupUseCase
    ): LeaveGroupUseCase
}