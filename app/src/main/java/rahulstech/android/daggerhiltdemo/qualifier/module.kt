package rahulstech.android.daggerhiltdemo.qualifier

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named // NOTE: use from javax package
import javax.inject.Qualifier // NOTE: use from javax package

/**
 * 👉 When to use @Named
 * - When i have multiple bindings of the same type (e.g., two AuthenticatorService).
 * - Use @Named("...") to differentiate them.
 *
 * 👉 Limitations
 * - String-based, so prone to typos.
 *
 * 👉 Real-world scenarios
 * - Multiple API clients (e.g., staging vs production).
 * - Different cache implementations (in-memory vs disk).
 * - Different flavors of services (Google vs Facebook).
 */

//@Module
//@InstallIn(SingletonComponent::class)
//object AuthModule2 { // NOTE: object is used instead of abstract class; because @Provides is used
//
//    @Provides // NOTE: use @Provides with @Named
//    @Named("google")
//    fun getGoogleAuthenticator(): AuthService = GoogleAuthService()
//
//    @Provides
//    @Named("apple")
//    fun getAppleAuthenticator(): AuthService = AppleAuthService()
//}

/**
 * i created two @Qualifier s GoogleAuth and AppleAuth to replace @Named("google") and @Named("apple") respectively.
 * this approach is safer as @Named provider can lead to provider not found due to typos.
 *
 * NOTE:
 * - @Qualifiers does not takes any properties
 * - Always use @Retention(BINARY) for Hilt/Dagger qualifiers as Dagger annotation processed at compile-time, not runtime.
*/

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class GoogleAuth

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AppleAuth

@Module
@InstallIn(SingletonComponent::class)
object AuthModule3 {

    @Provides
    @GoogleAuth // instead of @Named now i am using that particular quantifier
    fun getGoogleAuthService(): AuthService = GoogleAuthService()

    @Provides
    @AppleAuth
    fun getAppAuthService(): AuthService = AppleAuthService()
}