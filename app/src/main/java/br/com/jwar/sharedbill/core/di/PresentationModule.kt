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
    fun provideUserToUserUiModel(): UserToUserUiModelMapper =
        UserToUserUiModelMapperImpl()

    @Provides
    fun providePaymentToPaymentUiModel(): PaymentToPaymentUiModelMapper =
        PaymentToPaymentUiModelMapperImpl()

    @Provides
    fun provideGroupToGroupUiModelMapper(
        paymentToPaymentUiModelMapper: PaymentToPaymentUiModelMapper
    ): GroupToGroupUiModelMapper =
        GroupToGroupUiModelMapperImpl(paymentToPaymentUiModelMapper)
}