package rahulstech.android.daggerhiltdemo

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import rahulstech.android.daggerhiltdemo.basic.BasicDIActivity
import rahulstech.android.daggerhiltdemo.binding.LogInActivity
import rahulstech.android.daggerhiltdemo.databinding.ActivityMainBinding
import rahulstech.android.daggerhiltdemo.lifecycle.HiltLifecycleActivity
import java.util.Arrays

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var adapter: ArrayAdapter<CharSequence>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ArrayAdapter.createFromResource(this,R.array.nav_items,android.R.layout.simple_list_item_1)
        binding.navigation.adapter = adapter
        binding.navigation.onItemClickListener = AdapterView.OnItemClickListener { _,_,position,_ ->
            val clazz = when (position) {
                0 -> BasicDIActivity::class.java
                1 -> LogInActivity::class.java
                2 -> HiltLifecycleActivity::class.java
                else -> null
            }
            if (null != clazz) {
                startActivity(Intent(this, clazz))
            }
        }
    }
}