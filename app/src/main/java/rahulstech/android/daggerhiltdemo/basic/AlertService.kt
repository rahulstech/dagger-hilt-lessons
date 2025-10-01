package rahulstech.android.daggerhiltdemo.basic

import android.content.Context
import android.util.Log
import android.widget.Toast
import dagger.hilt.android.qualifiers.ActivityContext

import javax.inject.Inject /* NOTE: always use Inject form javax package */


class AlertService
    // when its instance is injected then use this constructor
    // if there is no constructor with @Inject then DI can not decide how to create instance
    @Inject constructor(

    // this tells how to  provide or bind the bind context argument
    // it instructs to use caller activity component context for this argument
    @ActivityContext
    private val context: Context

) {
        private val TAG = "AlertService"

        // this constructor will never be used by DI
        // DI can not even call this constructor because it has no idea how to get context and message
        constructor(context: Context, message: String) : this(context) {
            Log.i(TAG,"the constructor message $message")
        }

    fun shortAlert(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun longAlert(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}