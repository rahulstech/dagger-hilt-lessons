package rahulstech.android.daggerhiltdemo.contextinjection

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import rahulstech.android.daggerhiltdemo.R
import rahulstech.android.daggerhiltdemo.databinding.ActivityColorPickerBinding
import javax.inject.Inject


/*
Injecting Android Framework Types

1. @ApplicationContext:
   - Provides app-level context (singleton scope).
   - Safe to use for SharedPreferences, databases, file access, etc.

2. @ActivityContext:
   - Provides activity-level context (lifecycle tied to activity).
   - Safe to use for UI-related components like Dialogs, Toasts, Views.

3. Why use qualifiers?
   - Both @ApplicationContext and @ActivityContext are Context.
   - Hilt needs a qualifier to know which Context to inject.

4. Mini Project Scenario:
   - Color Picker app where:
     * SharedPreferences store the preferred color.
     * A dialog shows a color palette.
     * Activity injects the preference manager and dialog manager.
   - Injection allows decoupling UI from framework logic.

5. Key takeaway:
   - Android framework types (Context, SharedPreferences, Resources) should be injected using Hilt.
   - Always use qualifiers to avoid ambiguity.
*/


@AndroidEntryPoint
class ColorPickerActivityBaseTheme : AppCompatActivity() {

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_color_picker_activity_base_theme, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.goto_activity_new_theme) {
            startActivity(Intent(this, ColorPickerActivityNewTheme::class.java))
        }
        return super.onOptionsItemSelected(item)
    }
}