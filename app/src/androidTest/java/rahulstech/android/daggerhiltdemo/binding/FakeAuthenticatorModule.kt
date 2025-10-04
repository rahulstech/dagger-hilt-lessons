package rahulstech.android.daggerhiltdemo.binding

import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
// the following annotation replace the actual Module with this module for test purpose only
@TestInstallIn(
    components = [SingletonComponent::class], // this must be same as the actual module
    replaces = [AuthenticatorModule::class] 
)
abstract class FakeAuthenticatorModule {

    @Binds
    abstract fun getAuthenticatorService(impl: FakeAuthenticatorService): AuthenticatorService
}
