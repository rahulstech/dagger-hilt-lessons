package rahulstech.android.daggerhiltdemo.binding

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import rahulstech.android.daggerhiltdemo.binding.impl.AuthenticatorServiceImpl
import javax.inject.Singleton

/**
 * =======================
 * Notes on @Binds vs @Provides:
 * =======================
 *
 * 1) @Binds
 *    - Used for mapping an interface to an implementation.
 *    - Requires the implementation class to have an @Inject constructor
 *      so that Hilt knows how to build it.
 *    - Must be placed in an abstract function (no body).
 *    - Therefore, modules that use @Binds must be abstract classes.
 *
 *    Example:
 *    ```kotlin
 *        @Binds
 *        abstract fun bindAuthenticatorService(
 *            impl: AuthenticatorServiceImpl
 *        ): AuthenticatorService
 *    ```
 *
 * ------------------------------------------------------
 *
 * 2) @Provides
 *    - Used when you need to construct/return an object manually.
 *    - Useful when:
 *         - You return primitives (String, Int, Boolean).
 *         - You use third-party classes (can’t put @Inject in their constructor).
 *         - You need runtime/config values (e.g., BuildConfig.BASE_URL).
 *         - You want to apply extra logic before returning an object.
 *    - Provides methods can live inside an `object` module
 *      (no need to instantiate the module) OR inside a companion object of an abstract module.
 *
 *    Example:
 *    ```kotlin
 *        @Provides
 *        fun provideEnv(): String = "google"
 *    ```
 *
 * ------------------------------------------------------
 *
 * 3) Module container types:
 *
 *   - `object` module:
 *        • Use when you have only @Provides methods.
 *        • It is a Kotlin singleton, so Dagger/Hilt can call provider methods directly.
 *
 *   - `abstract class` module:
 *        • Use when you have @Binds methods (because they must be abstract).
 *        • If you also need @Provides, put them in a `companion object`
 *          and annotate with @JvmStatic (so Dagger can call them without instantiating the class).
 *
 * ------------------------------------------------------
 *
 * 4) Scoping:
 *    - Add @Singleton on @Provides/@Binds if you want a single instance
 *      for the whole application lifetime (per SingletonComponent).
 *    - Without @Singleton, Hilt will create a new instance each time
 *      the dependency is injected.
 */

//@Module
//@InstallIn(SingletonComponent::class)
//abstract class AuthenticatorModule {
//
//    // @Binds: Simple interface -> implementation mapping
//    // AuthenticatorServiceImpl must have @Inject constructor(env: String)
//    @Binds
//    @Singleton
//    abstract fun bindAuthenticatorService(
//        impl: AuthenticatorServiceImpl
//    ): AuthenticatorService
//
//    companion object {
//        // @Provides: Example of providing a primitive/config value
//        // This value can be injected into AuthenticatorServiceImpl
//        @Provides
//        @JvmStatic
//        fun provideEnv(): String = "google"
//    }
//}

//@Module
//@InstallIn(SingletonComponent::class)
//abstract class AuthenticatorModule {
//
//    @Binds
//    abstract fun bindAuthenticatorService(
//        service: AuthenticatorServiceImpl
//    ): AuthenticatorService
//    }
//}

@Module
@InstallIn(SingletonComponent::class)
object AuthenticatorModule {

    @Provides
    fun providesProvider(): String = "google"

    @Provides
    @Singleton
    fun provideAuthenticatorService(provider: String): AuthenticatorService {
        return AuthenticatorServiceImpl(provider)
    }
}