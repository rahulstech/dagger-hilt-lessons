package rahulstech.android.daggerhiltdemo.basic

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import rahulstech.android.daggerhiltdemo.databinding.ActivityBasicDiactivityBinding
import javax.inject.Inject

// marks the activity as DI component
@AndroidEntryPoint
class BasicDIActivity : AppCompatActivity() {

    @Inject //  use inject here to tell DI to provide the instance
    lateinit var alertService: AlertService

    private lateinit var binding: ActivityBasicDiactivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBasicDiactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            val text = binding.edittext.text.toString()
            if (text.isNotBlank()) {
                alertService.longAlert(text)
            }
        }

        binding.button2.setOnClickListener {
            val text = binding.edittext.text.toString()
            if (text.isNotBlank()) {
                AlertService(this, "manually instantiated").longAlert(text)
            }
        }
    }
}