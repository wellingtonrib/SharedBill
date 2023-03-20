package br.com.jwar.sharedbill.data.account.di

import br.com.jwar.sharedbill.data.account.datasources.FirebaseUserDataSource
import br.com.jwar.sharedbill.data.account.datasources.UserDataSource
import br.com.jwar.sharedbill.data.account.mappers.FirebaseUserToUserMapper
import br.com.jwar.sharedbill.data.account.mappers.FirebaseUserToUserMapperImpl
import br.com.jwar.sharedbill.data.account.repositories.DefaultUserRepository
import br.com.jwar.sharedbill.data.account.services.FirebaseAuthService
import br.com.jwar.sharedbill.domain.account.repositories.UserRepository
import br.com.jwar.sharedbill.domain.account.services.AuthService
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
    fun provideAuthService(
        firebaseAuth: FirebaseAuth,
        signInClient: SignInClient,
        @Named(FirebaseModule.SIGN_UP_REQUEST) signUpRequest: BeginSignInRequest,
        @Named(FirebaseModule.SIGN_IN_REQUEST) signInRequest: BeginSignInRequest,
        firebaseUserToUserMapper: FirebaseUserToUserMapper,
        userRepository: UserRepository
    ): AuthService = FirebaseAuthService(
        firebaseAuth = firebaseAuth,
        signInClient = signInClient,
        signInRequest = signUpRequest,
        signUpRequest = signInRequest,
        firebaseUserToUserMapper = firebaseUserToUserMapper,
        userRepository = userRepository,
    )

    @Provides
    @Singleton
    fun provideUserDataSource(
        firebaseAuth: FirebaseAuth,
        firestore: FirebaseFirestore,
        firebaseUserToUserMapper: FirebaseUserToUserMapper
    ): UserDataSource = FirebaseUserDataSource(
        firebaseAuth = firebaseAuth,
        firestore = firestore,
        firebaseUserToUserMapper = firebaseUserToUserMapper
    )

    @Provides
    @Singleton
    fun provideUserRepository(
        userDataSource: UserDataSource,
    ): UserRepository = DefaultUserRepository(
        userDataSource = userDataSource
    )

    @Provides
    @Singleton
    fun provideFirebaseUserToUserMapper(): FirebaseUserToUserMapper =
        FirebaseUserToUserMapperImpl()
}