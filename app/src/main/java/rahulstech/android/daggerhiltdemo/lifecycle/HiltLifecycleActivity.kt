package rahulstech.android.daggerhiltdemo.lifecycle

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import rahulstech.android.daggerhiltdemo.R
import rahulstech.android.daggerhiltdemo.databinding.ActivityHiltLifecycleBinding
import javax.inject.Inject

private const val KEY_CURRENT_PAGE_INDEX = "key_current_page_index"

@AndroidEntryPoint
class HiltLifecycleActivity : AppCompatActivity() {

    @Inject lateinit var appService: AppScopedService
    @Inject lateinit var activityService: ActivityScopedService

    private lateinit var binding: ActivityHiltLifecycleBinding
    private var currentPage: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHiltLifecycleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnPage1.setOnClickListener { changePage(1) }
        binding.btnPage2.setOnClickListener { changePage(2) }
        savedInstanceState?.apply {
            currentPage = getInt(KEY_CURRENT_PAGE_INDEX)
        }
        changePage(currentPage)
    }

    fun changePage(index: Int) {
        if (index == currentPage) return
        var page = PageFragment.newInstance(index)
        supportFragmentManager.beginTransaction().replace(R.id.container, page).commit()
        currentPage = index
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.apply {
            putInt(KEY_CURRENT_PAGE_INDEX,currentPage)
        }
    }
}