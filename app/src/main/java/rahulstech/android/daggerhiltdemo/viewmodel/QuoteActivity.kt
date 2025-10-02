package rahulstech.android.daggerhiltdemo.viewmodel

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import rahulstech.android.daggerhiltdemo.databinding.ActivityQuoteBinding

@AndroidEntryPoint
class QuoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuoteBinding

    private val viewModel: QuoteViewModel by viewModels<QuoteViewModel>() // must generate lazily

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnRefresh.setOnClickListener { refresh() }
        refresh()
    }

    fun refresh() {
        binding.displayQuote.text = viewModel.getRandomQuote()
    }
}