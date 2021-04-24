package project.android.todoapp.ui.main.add_task

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.transition.Slide
import com.google.android.material.transition.MaterialContainerTransform
import project.android.todoapp.R
import project.android.todoapp.databinding.FragmentAddTaskBinding
import project.android.todoapp.model.Tag

class FragmentAddTask : Fragment() {
    private var _binding : FragmentAddTaskBinding? = null
    val binding : FragmentAddTaskBinding get() = _binding!!
    private var tag = Tag.OTHER
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddTaskBinding.inflate(inflater,container,false)
        setAdapters()
        return binding.root
    }

    private fun setAdapters() {
        val adapter = TagAdapter(
            requireContext(),
            R.layout.item_tag_dropdown,
            enumValues()
        )
        binding.editAddTaskDropdown.setAdapter(adapter)
        binding.editAddTaskDropdown.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                tag = enumValues<Tag>()[position]
            }
    }


}
class TagAdapter(context: Context, resource: Int, private val items: Array<out Tag>) :
    ArrayAdapter<Tag>(context, resource, items) {

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tag_dropdown, parent, false)
        val imageView = view.findViewById<ImageView>(R.id.task_tag_color)
        imageView.setColorFilter(ContextCompat.getColor(context, items[position].resId))
        val textView = view.findViewById<TextView>(R.id.task_tag_description)
        textView.text = items[position].description
        return view
    }
}