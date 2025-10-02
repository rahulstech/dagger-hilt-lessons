package rahulstech.android.daggerhiltdemo.qualifier

import javax.inject.Inject

interface AuthService {

    fun singIn(username: String, password: String): String
}

class GoogleAuthService @Inject constructor(): AuthService {
    override fun singIn(username: String, password: String): String {
        return "google-access-token::$username:$password"
    }
}

class AppleAuthService @Inject constructor(): AuthService {
    override fun singIn(username: String, password: String): String {
        return "apple-access-toke::$username:$password"
    }
}