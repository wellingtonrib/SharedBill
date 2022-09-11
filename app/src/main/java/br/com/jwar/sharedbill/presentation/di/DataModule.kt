package br.com.jwar.sharedbill.presentation.di

import br.com.jwar.sharedbill.data.datasources.FirebaseUserDataSource
import br.com.jwar.sharedbill.data.mappers.FirebaseUserToUserMapper
import br.com.jwar.sharedbill.data.mappers.FirebaseUserToUserMapperImpl
import br.com.jwar.sharedbill.data.repositories.DefaultUserRepository
import br.com.jwar.sharedbill.data.services.FirebaseAuthService
import br.com.jwar.sharedbill.domain.datasources.UserDataSource
import br.com.jwar.sharedbill.domain.repositories.UserRepository
import br.com.jwar.sharedbill.domain.services.AuthService
import br.com.jwar.sharedbill.presentation.di.FirebaseModule.Companion.SIGN_IN_REQUEST
import br.com.jwar.sharedbill.presentation.di.FirebaseModule.Companion.SIGN_UP_REQUEST
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun provideAuthService(
        firebaseAuth: FirebaseAuth,
        signInClient: SignInClient,
        @Named(SIGN_UP_REQUEST) signUpRequest: BeginSignInRequest,
        @Named(SIGN_IN_REQUEST) signInRequest: BeginSignInRequest,
        firebaseUserToUserMapper: FirebaseUserToUserMapper,
        userDataSource: UserDataSource
    ): AuthService = FirebaseAuthService(
        firebaseAuth = firebaseAuth,
        signInClient = signInClient,
        signInRequest = signUpRequest,
        signUpRequest = signInRequest,
        firebaseUserToUserMapper = firebaseUserToUserMapper,
        userDataSource = userDataSource,
    )

    @Provides
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
    fun provideUserRepository(
        userDataSource: UserDataSource,
    ): UserRepository = DefaultUserRepository(
        userDataSource = userDataSource
    )

    @Provides
    fun provideFirebaseUserToUserMapper(): FirebaseUserToUserMapper = FirebaseUserToUserMapperImpl()
}