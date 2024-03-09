package br.com.jwar.sharedbill.groups.presentation.di

import br.com.jwar.sharedbill.groups.presentation.mappers.GroupToGroupUiModelMapper
import br.com.jwar.sharedbill.groups.presentation.mappers.GroupToGroupUiModelMapperImpl
import br.com.jwar.sharedbill.groups.presentation.mappers.PaymentToPaymentUiModelMapper
import br.com.jwar.sharedbill.groups.presentation.mappers.PaymentToPaymentUiModelMapperImpl
import br.com.jwar.sharedbill.groups.presentation.mappers.UserToGroupMemberUiModelMapper
import br.com.jwar.sharedbill.groups.presentation.mappers.UserToGroupMemberUiModelMapperImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class PresentationModule {
    @Provides
    fun provideUserToGroupMemberUiModel(): UserToGroupMemberUiModelMapper =
        UserToGroupMemberUiModelMapperImpl()

    @Provides
    fun providePaymentToPaymentUiModel(
        userToUserUiModelMapper: UserToGroupMemberUiModelMapper
    ): PaymentToPaymentUiModelMapper =
        PaymentToPaymentUiModelMapperImpl(userToUserUiModelMapper)

    @Provides
    fun provideGroupToGroupUiModelMapper(
        paymentToPaymentUiModelMapper: PaymentToPaymentUiModelMapper,
        userToGroupMemberUiModelMapper: UserToGroupMemberUiModelMapper
    ): GroupToGroupUiModelMapper =
        GroupToGroupUiModelMapperImpl(
            paymentToPaymentUiModelMapper,
            userToGroupMemberUiModelMapper
        )
}
