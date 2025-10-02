package rahulstech.android.daggerhiltdemo.qualifier

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import rahulstech.android.daggerhiltdemo.R
import rahulstech.android.daggerhiltdemo.databinding.ActivityLogIn2Binding
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class LogInActivity2 : AppCompatActivity() {

//    @Inject
//    @Named("google") // NOTE: how @Named annotation is used
//    lateinit var googleAuthenticator: AuthService
//
//    @Inject
//    @Named("apple")
//    lateinit var appleAuthenticator: AuthService

    @Inject
    @GoogleAuth
    lateinit var googleAuthenticator: AuthService

    @Inject
    @AppleAuth
    lateinit var appleAuthenticator: AuthService

    private lateinit var binding: ActivityLogIn2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogIn2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnLogin.setOnClickListener { login() }
    }

    fun login() {
        val username = binding.inputUsername.text.toString()
        val password = binding.inputPassword.text.toString()
        val authenticator = binding.authenticator.checkedRadioButtonId

        val token = when(authenticator) {
            R.id.auth_google -> googleAuthenticator.singIn(username,password)
            R.id.auth_apple -> appleAuthenticator.singIn(username,password)
            else -> null
        }
        if (null == token) {
            Toast.makeText(this,"no authenticator selected", Toast.LENGTH_LONG).show()
            return
        }

        binding.output.text = token
    }
}