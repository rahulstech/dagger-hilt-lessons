package rahulstech.android.daggerhiltdemo.scoped_hierarchy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import rahulstech.android.daggerhiltdemo.R

// nothing is injected in activity still i need to add @AndroidEntryPoint
// because the inflated fragment is an @AndroidEntryPoint. otherwise there will be an inflate error
@AndroidEntryPoint
class CounterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_counter)
    }
}