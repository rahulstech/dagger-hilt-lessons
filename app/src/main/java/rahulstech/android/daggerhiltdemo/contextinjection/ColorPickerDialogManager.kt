package rahulstech.android.daggerhiltdemo.contextinjection

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import androidx.annotation.ColorInt
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ActivityContext
import rahulstech.android.daggerhiltdemo.R
import rahulstech.android.daggerhiltdemo.databinding.ColorPickerDialogItemBinding
import rahulstech.android.daggerhiltdemo.databinding.ColorPickerDialogLayoutBinding
import javax.inject.Inject

typealias ColorPickerCallback = (color: Int)-> Unit

class ColorAdapter(context: Context, private val colors: List<Int>): BaseAdapter() {

    private val layoutInflater = LayoutInflater.from(context)

    override fun getCount(): Int = colors.size

    @ColorInt override fun getItem(position: Int): Int = colors[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup?
    ): View? {
        var binding: ColorPickerDialogItemBinding
        if (null != convertView) {
            binding = convertView.tag as ColorPickerDialogItemBinding
        }
        else {
            binding = ColorPickerDialogItemBinding.inflate(layoutInflater, parent, false)
            binding.root.tag = binding
        }
        binding.colorView.setBackgroundColor(getItem(position))
        return binding.root
    }
}

class ColorPickerDialog(context: Context, private val colors: List<Int>): AlertDialog(context) {

    private lateinit var binding: ColorPickerDialogLayoutBinding

    private val adapter: ColorAdapter by lazy { ColorAdapter(context,colors) }

    private var callback: ColorPickerCallback? = null

    private var lastPicked: Int = colors[0]

    fun setColorPickerCallback(callback: ColorPickerCallback?) {
        this.callback = callback
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ColorPickerDialogLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.listView.adapter = adapter
        binding.listView.onItemClickListener = AdapterView.OnItemClickListener { _,_,position,_ ->
            lastPicked = adapter.getItem(position)
        }
        binding.btnPick.setOnClickListener { pick() }
        binding.btnCancel.setOnClickListener { cancel() }
    }

    private fun pick() {
        callback?.invoke(lastPicked)
        dismiss()
    }
}

class ColorPickerDialogManager @Inject constructor(

    // based on the injected activity, the dialog theme will change
    @ActivityContext private val context: Context
) {
    private val COLORS = listOf(
        ContextCompat.getColor(context, R.color.color1),
        ContextCompat.getColor(context, R.color.color2),
        ContextCompat.getColor(context, R.color.color3),
        ContextCompat.getColor(context, R.color.color4),
        ContextCompat.getColor(context, R.color.color5),
        ContextCompat.getColor(context, R.color.color6),
        ContextCompat.getColor(context, R.color.color7),
        ContextCompat.getColor(context, R.color.color8),
        ContextCompat.getColor(context, R.color.color9),
        ContextCompat.getColor(context, R.color.color10),
        ContextCompat.getColor(context, R.color.color11),
        ContextCompat.getColor(context, R.color.color12),
        ContextCompat.getColor(context, R.color.color13),
        ContextCompat.getColor(context, R.color.color14),
        ContextCompat.getColor(context, R.color.color15),
        ContextCompat.getColor(context, R.color.color16),
        ContextCompat.getColor(context, R.color.color17),
        ContextCompat.getColor(context, R.color.color18),
        ContextCompat.getColor(context, R.color.color19),
        ContextCompat.getColor(context, R.color.color20),
        ContextCompat.getColor(context, R.color.color21),
        ContextCompat.getColor(context, R.color.color22),
        ContextCompat.getColor(context, R.color.color23),
        ContextCompat.getColor(context, R.color.color24),
        ContextCompat.getColor(context, R.color.color25),
    )

    fun showColorPicker(callback: ColorPickerCallback) {
        val dialog = ColorPickerDialog(context,COLORS)
        dialog.setColorPickerCallback(callback)
        dialog.show()
    }
}