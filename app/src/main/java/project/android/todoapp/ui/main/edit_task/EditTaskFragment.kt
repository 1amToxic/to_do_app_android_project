package project.android.todoapp.ui.main.edit_task

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import project.android.todoapp.R
import project.android.todoapp.ToDoApplication
import project.android.todoapp.databinding.FragmentEditTaskBinding
import project.android.todoapp.model.Tag
import project.android.todoapp.ui.main.add_task.adapter.TagAdapter
import project.android.todoapp.utils.DateStringConverter
import project.android.todoapp.viewmodel.TaskViewModel
import project.android.todoapp.viewmodel.factory.TaskViewModelFactory


class EditTaskFragment : Fragment() {
    private lateinit var binding : FragmentEditTaskBinding
    private var tag = Tag.OTHER
    private val taskViewModel: TaskViewModel by lazy {
        val app = ToDoApplication()
        val viewModelProviderFactory =
            TaskViewModelFactory(app)
        ViewModelProvider(
            requireActivity(),
            viewModelProviderFactory
        )[TaskViewModel::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditTaskBinding.inflate(inflater,container,false)
        initView()
        return binding.root
    }
    private fun initView(){
        taskViewModel.taskDetailDisplay.observe(viewLifecycleOwner, Observer {
            it.let {
                binding.editTaskName.setText(it.title)
                binding.editTaskDescription.setText(it.content)
                binding.editTaskDate.setText(DateStringConverter.dateToString(it.date!!))
                binding.editTaskDropdown.setText(enumValueOf<Tag>(it.tag.name).description)
            }
        })
        setAdaptersDropDown()
    }
    private fun setAdaptersDropDown() {
        val adapter = TagAdapter(
            requireContext(),
            R.layout.item_tag_dropdown,
            enumValues()
        )
        binding.editTaskDropdown.setAdapter(adapter)
        binding.editTaskDropdown.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                tag = enumValues<Tag>()[position]
            }
    }

}