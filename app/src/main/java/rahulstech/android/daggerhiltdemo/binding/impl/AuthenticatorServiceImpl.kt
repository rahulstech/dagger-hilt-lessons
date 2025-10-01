package rahulstech.android.daggerhiltdemo.binding.impl

import rahulstech.android.daggerhiltdemo.binding.AuthenticatorService
import javax.inject.Inject
import javax.inject.Singleton /* NOTE: use annotation from javax package */

// hints dagger hilt to store singleton instance app-wide, without it dagger will create new instance on each inject
// NOTE: it is better to use @Singleton in @Binds/@Provides method than the class
//@Singleton
class AuthenticatorServiceImpl
    // @Inject constructor() // i can use @Binds for this constructor
    @Inject constructor(
        private val provider: String
    )
    : AuthenticatorService {

    override fun login(username: String, password: String): String {
        return "${provider}-token::$username:$password"
    }
}