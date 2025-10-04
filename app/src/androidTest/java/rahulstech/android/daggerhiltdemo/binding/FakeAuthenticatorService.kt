package rahulstech.android.daggerhiltdemo.binding

import javax.inject.Inject

class FakeAuthenticatorService @Inject constructor() : AuthenticatorService {

    override fun login(username: String, password: String): String = "FAKE_TOKEN"
}