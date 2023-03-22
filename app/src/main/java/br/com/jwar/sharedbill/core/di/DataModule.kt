package br.com.jwar.sharedbill.core.di

import br.com.jwar.sharedbill.data.datasources.FirebaseGroupsDataSource
import br.com.jwar.sharedbill.account.data.datasources.FirebaseUserDataSource
import br.com.jwar.sharedbill.account.data.mappers.FirebaseUserToUserMapper
import br.com.jwar.sharedbill.account.data.mappers.FirebaseUserToUserMapperImpl
import br.com.jwar.sharedbill.data.repositories.DefaultGroupRepository
import br.com.jwar.sharedbill.account.data.repositories.DefaultUserRepository
import br.com.jwar.sharedbill.account.data.services.FirebaseAuthService
import br.com.jwar.sharedbill.domain.datasources.GroupsDataSource
import br.com.jwar.sharedbill.account.data.datasources.UserDataSource
import br.com.jwar.sharedbill.domain.repositories.GroupRepository
import br.com.jwar.sharedbill.account.domain.repositories.UserRepository
import br.com.jwar.sharedbill.account.domain.services.AuthService
import br.com.jwar.sharedbill.account.data.di.FirebaseModule.Companion.SIGN_IN_REQUEST
import br.com.jwar.sharedbill.account.data.di.FirebaseModule.Companion.SIGN_UP_REQUEST
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideGroupsDataSource(
        firebaseAuth: FirebaseAuth,
        firestore: FirebaseFirestore,
    ): GroupsDataSource = FirebaseGroupsDataSource(
        firebaseAuth = firebaseAuth,
        firestore = firestore
    )

    @Provides
    @Singleton
    fun provideGroupsRepository(
        groupsDataSource: GroupsDataSource,
    ): GroupRepository = DefaultGroupRepository(
        groupsDataSource = groupsDataSource,
    )
}