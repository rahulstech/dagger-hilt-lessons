package rahulstech.android.daggerhiltdemo.binding

interface AuthenticatorService {

    fun login(username: String, password: String): String
}