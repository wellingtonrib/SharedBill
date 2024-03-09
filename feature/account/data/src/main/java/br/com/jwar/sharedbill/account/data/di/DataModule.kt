package br.com.jwar.sharedbill.account.data.di

import br.com.jwar.sharedbill.account.data.datasources.FirebaseUserDataSource
import br.com.jwar.sharedbill.account.data.datasources.UserDataSource
import br.com.jwar.sharedbill.account.data.mappers.FirebaseUserToUserMapper
import br.com.jwar.sharedbill.account.data.mappers.FirebaseUserToUserMapperImpl
import br.com.jwar.sharedbill.account.data.repositories.DefaultUserRepository
import br.com.jwar.sharedbill.account.data.services.FirebaseAuthService
import br.com.jwar.sharedbill.account.domain.repositories.UserRepository
import br.com.jwar.sharedbill.account.domain.services.AuthService
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
