package rahulstech.android.daggerhiltdemo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class QuoteViewModel @Inject constructor(
    // Application instance is auto passed by hilt
    app: Application,

    // QuoteRepository is concrete class therefore no need for a Provider
    // If QuoteRepository be an interface/abstract class then i need a Module class and provide the implementation
    private val repository: QuotesRepository
) : AndroidViewModel(app) {

    fun getRandomQuote(): String = repository.getRandomQuote()
}