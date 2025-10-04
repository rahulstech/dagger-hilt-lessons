// app/src/androidTest/java/rahulstech/android/daggerhiltdemo/HiltTestRunner.kt
package rahulstech.android.daggerhiltdemo

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import dagger.hilt.android.testing.HiltTestApplication

class HiltTestRunner : AndroidJUnitRunner() {
    override fun newApplication(cl: ClassLoader?, name: String?, context: Context?): Application {
        // Replace normal Application with Hilt’s special test Application
        return super.newApplication(cl, HiltTestApplication::class.java.name, context)
    }
}
