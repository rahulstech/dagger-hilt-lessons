package rahulstech.android.daggerhiltdemo.contextinjection

import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import rahulstech.android.daggerhiltdemo.R
import javax.inject.Inject

class ColorPreferenceManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val prefs: SharedPreferences = context.getSharedPreferences("color_preference", Context.MODE_PRIVATE)

    @ColorInt fun getColor(): Int = prefs.getInt("COLOR", ContextCompat.getColor(context,R.color.color1))

    fun setColor(@ColorInt color: Int) {
        prefs.edit(true) {
            putInt("COLOR",color)
        }
    }
}