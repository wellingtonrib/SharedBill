package br.com.jwar.groups.presentation.di

import br.com.jwar.groups.presentation.mappers.GroupToGroupUiModelMapper
import br.com.jwar.groups.presentation.mappers.GroupToGroupUiModelMapperImpl
import br.com.jwar.groups.presentation.mappers.GroupToPaymentParamsMapper
import br.com.jwar.groups.presentation.mappers.GroupToPaymentParamsMapperImpl
import br.com.jwar.groups.presentation.mappers.PaymentToPaymentUiModelMapper
import br.com.jwar.groups.presentation.mappers.PaymentToPaymentUiModelMapperImpl
import br.com.jwar.groups.presentation.mappers.UserToGroupMemberUiModelMapper
import br.com.jwar.groups.presentation.mappers.UserToGroupMemberUiModelMapperImpl
import br.com.jwar.sharedbill.core.utility.StringProvider
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
        GroupToGroupUiModelMapperImpl(
            paymentToPaymentUiModelMapper,
            userToGroupMemberUiModelMapper
        )

    @Provides
    fun provideGroupToPaymentParamsMapper(
        groupToGroupUiModelMapper: GroupToGroupUiModelMapper,
        userToGroupMemberUiModelMapper: UserToGroupMemberUiModelMapper,
        stringProvider: StringProvider,
    ): GroupToPaymentParamsMapper =
        GroupToPaymentParamsMapperImpl(
            groupToGroupUiModelMapper,
            userToGroupMemberUiModelMapper,
            stringProvider,
        )
}