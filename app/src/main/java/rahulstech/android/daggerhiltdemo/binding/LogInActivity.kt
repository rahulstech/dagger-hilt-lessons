package rahulstech.android.daggerhiltdemo.binding

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import dagger.hilt.android.AndroidEntryPoint
import rahulstech.android.daggerhiltdemo.R
import rahulstech.android.daggerhiltdemo.databinding.ActivityLogInBinding
import javax.inject.Inject

@AndroidEntryPoint
class LogInActivity : AppCompatActivity() {

    @Inject lateinit var authenticator: AuthenticatorService

    private lateinit var binding: ActivityLogInBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener { onClickLogin() }
    }

    fun onClickLogin() {
        val username = binding.inputUsername.text.toString()
        val password = binding.inputPassword.text.toString()

        val token = authenticator.login(username,password)
        binding.output.text = token
    }
}