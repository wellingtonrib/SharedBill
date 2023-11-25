package br.com.jwar.sharedbill

import br.com.jwar.sharedbill.core.utility.StringProvider
import br.com.jwar.sharedbill.di.AppModule
import br.com.jwar.sharedbill.groups.domain.di.DomainModule
import br.com.jwar.sharedbill.groups.domain.model.Group
import br.com.jwar.sharedbill.groups.domain.model.Payment
import br.com.jwar.sharedbill.groups.domain.model.PaymentType
import br.com.jwar.sharedbill.groups.domain.usecases.*
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AppModule::class]
)
abstract class FakeAppModule {

    class FakeStringProvider: StringProvider {
        override fun getString(resId: Int): String {
            return ""
        }
    }

    @Binds
    @Singleton
    abstract fun bindStringProvider(
        fake: FakeStringProvider
    ): StringProvider

}