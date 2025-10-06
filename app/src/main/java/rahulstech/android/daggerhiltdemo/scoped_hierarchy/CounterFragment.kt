package rahulstech.android.daggerhiltdemo.scoped_hierarchy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import rahulstech.android.daggerhiltdemo.databinding.FragmentCounterBinding
import javax.inject.Inject

@AndroidEntryPoint
class CounterFragment : Fragment() {

    @Inject
    lateinit var fragmentUIManager: FragmentUIManager

    @Inject
    lateinit var activityLogger: ActivityLogger

    private lateinit var binding: FragmentCounterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCounterBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.incrementCounter.setOnClickListener {
            val value = fragmentUIManager.handleIncrement()
            activityLogger.logIncrement()
            binding.showCounter.text = value.toString()
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = CounterFragment()
    }
}