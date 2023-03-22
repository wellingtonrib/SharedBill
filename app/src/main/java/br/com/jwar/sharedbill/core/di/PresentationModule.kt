package br.com.jwar.sharedbill.core.di

import br.com.jwar.sharedbill.presentation.mappers.*
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
    fun providePaymentToPaymentUiModel(): PaymentToPaymentUiModelMapper =
        PaymentToPaymentUiModelMapperImpl()

    @Provides
    fun provideGroupToGroupUiModelMapper(
        paymentToPaymentUiModelMapper: PaymentToPaymentUiModelMapper,
        userToGroupMemberUiModelMapper: UserToGroupMemberUiModelMapper
    ): GroupToGroupUiModelMapper =
        GroupToGroupUiModelMapperImpl(paymentToPaymentUiModelMapper, userToGroupMemberUiModelMapper)
}