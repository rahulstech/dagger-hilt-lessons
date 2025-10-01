package rahulstech.android.daggerhiltdemo.lifecycle

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import rahulstech.android.daggerhiltdemo.R
import rahulstech.android.daggerhiltdemo.databinding.FragmentPageBinding
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 * Use the [PageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class PageFragment : Fragment() {

    @Inject lateinit var appService: AppScopedService
    @Inject lateinit var fragmentService: FragmentScopedService

    private var pageTitle: String = "Page -1"

    private lateinit var binding: FragmentPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            val index = getInt(PAGE_INDEX)
            pageTitle = "Page $index"
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPageBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.title.text = pageTitle
    }

    companion object {
        const val PAGE_INDEX = "page_index"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param index page index.
         * @return A new instance of fragment PageFragment.
         */
        @JvmStatic
        fun newInstance(index: Int) =
            PageFragment().apply {
                arguments = Bundle().apply {
                    putInt(PAGE_INDEX, index)
                }
            }
    }
}