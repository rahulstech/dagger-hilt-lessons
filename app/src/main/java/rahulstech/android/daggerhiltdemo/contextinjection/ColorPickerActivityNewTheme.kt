package rahulstech.android.daggerhiltdemo.contextinjection

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import dagger.hilt.android.AndroidEntryPoint
import rahulstech.android.daggerhiltdemo.R
import rahulstech.android.daggerhiltdemo.databinding.ActivityColorPickerBinding
import javax.inject.Inject

@AndroidEntryPoint
class ColorPickerActivityNewTheme : AppCompatActivity() {

    @Inject lateinit var preference: ColorPreferenceManager

    @Inject lateinit var picker: ColorPickerDialogManager

    private lateinit var binding: ActivityColorPickerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityColorPickerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnColorPicker.setOnClickListener { pickColor() }
        setPreferredColor(preference.getColor())
    }

    fun pickColor() {
        picker.showColorPicker { color -> changeColor(color) }
    }

    fun changeColor(@ColorInt color: Int) {
        preference.setColor(color)
        setPreferredColor(color)
    }

    fun setPreferredColor(@ColorInt color: Int) {
        binding.colorView.setBackgroundColor(color)
    }
}