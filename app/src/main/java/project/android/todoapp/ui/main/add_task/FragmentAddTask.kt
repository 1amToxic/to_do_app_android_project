package project.android.todoapp.ui.main.add_task


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import project.android.todoapp.R
import project.android.todoapp.databinding.FragmentAddTaskBinding
import project.android.todoapp.model.Tag
import project.android.todoapp.ui.main.add_task.adapter.TagAdapter

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
