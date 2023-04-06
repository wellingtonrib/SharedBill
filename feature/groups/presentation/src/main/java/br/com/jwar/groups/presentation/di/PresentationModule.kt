package br.com.jwar.groups.presentation.di

import br.com.jwar.groups.presentation.mappers.GroupToGroupUiModelMapper
import br.com.jwar.groups.presentation.mappers.GroupToGroupUiModelMapperImpl
import br.com.jwar.groups.presentation.mappers.PaymentToPaymentUiModelMapper
import br.com.jwar.groups.presentation.mappers.UserToGroupMemberUiModelMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class PresentationModule {

    @Provides
    fun provideUserToGroupMemberUiModel(): UserToGroupMemberUiModelMapper =
        br.com.jwar.groups.presentation.mappers.UserToGroupMemberUiModelMapperImpl()

    @Provides
    fun providePaymentToPaymentUiModel(): PaymentToPaymentUiModelMapper =
        br.com.jwar.groups.presentation.mappers.PaymentToPaymentUiModelMapperImpl()

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