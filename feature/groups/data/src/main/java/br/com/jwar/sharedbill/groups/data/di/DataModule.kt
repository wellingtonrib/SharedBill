package br.com.jwar.sharedbill.groups.data.di

import br.com.jwar.sharedbill.core.utility.ExceptionHandler
import br.com.jwar.sharedbill.core.utility.NetworkManager
import br.com.jwar.sharedbill.groups.data.datasources.FirebaseGroupsDataSource
import br.com.jwar.sharedbill.groups.data.datasources.GroupsDataSource
import br.com.jwar.sharedbill.groups.data.repositories.DefaultGroupRepository
import br.com.jwar.sharedbill.groups.domain.repositories.GroupRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideGroupsDataSource(
        firebaseAuth: FirebaseAuth,
        firestore: FirebaseFirestore,
        networkManager: NetworkManager,
        exceptionHandler: ExceptionHandler,
    ): GroupsDataSource = FirebaseGroupsDataSource(
        firebaseAuth = firebaseAuth,
        firestore = firestore,
        networkManager = networkManager,
        exceptionHandler = exceptionHandler,
    )

    @Provides
    @Singleton
    fun provideGroupsRepository(
        groupsDataSource: GroupsDataSource,
    ): GroupRepository = DefaultGroupRepository(
        groupsDataSource = groupsDataSource,
    )
}
