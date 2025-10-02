package rahulstech.android.daggerhiltdemo

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import rahulstech.android.daggerhiltdemo.basic.BasicDIActivity
import rahulstech.android.daggerhiltdemo.binding.LogInActivity
import rahulstech.android.daggerhiltdemo.databinding.ActivityMainBinding
import rahulstech.android.daggerhiltdemo.lifecycle.HiltLifecycleActivity
import rahulstech.android.daggerhiltdemo.qualifier.LogInActivity2
import rahulstech.android.daggerhiltdemo.viewmodel.QuoteActivity

class MainActivity : AppCompatActivity() {

    val CLASSES: List<Class<*>> = listOf(
        BasicDIActivity::class.java,
        LogInActivity::class.java,
        HiltLifecycleActivity::class.java,
        LogInActivity2::class.java,
        QuoteActivity::class.java
    )

    val NAMES = CLASSES.map { it.simpleName }

    private lateinit var binding: ActivityMainBinding

    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,NAMES);
        binding.navigation.adapter = adapter
        binding.navigation.onItemClickListener = AdapterView.OnItemClickListener { _,_,position,_ ->
            val clazz = CLASSES[position]
            startActivity(Intent(this, clazz))
        }
    }
}