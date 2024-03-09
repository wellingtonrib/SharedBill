package br.com.jwar.sharedbill.account.presentation.di

import br.com.jwar.sharedbill.account.presentation.mappers.UserToUserUiModelMapper
import br.com.jwar.sharedbill.account.presentation.mappers.UserToUserUiModelMapperImpl
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
}
