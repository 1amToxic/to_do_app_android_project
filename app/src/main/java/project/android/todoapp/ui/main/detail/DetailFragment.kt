package project.android.todoapp.ui.main.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import project.android.todoapp.R
import project.android.todoapp.ToDoApplication
import project.android.todoapp.databinding.FragmentDetailBinding
import project.android.todoapp.utils.Converters
import project.android.todoapp.utils.DateStringConverter
import project.android.todoapp.viewmodel.TaskViewModel
import project.android.todoapp.viewmodel.factory.TaskViewModelFactory


class DetailFragment : Fragment() {
    lateinit var binding : FragmentDetailBinding
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
        binding = FragmentDetailBinding.inflate(inflater,container,false)
        initView()
        setupListener()
        return binding.root
    }

    private fun setupListener() {
        binding.btnEditTask.setOnClickListener {
            findNavController().navigate(R.id.action_detailFragment_to_editTaskFragment)
        }
    }

    private fun initView() {
        taskViewModel.taskDetailDisplay.observe(viewLifecycleOwner, Observer {
            it.let {
                binding.textContentItemTaskDetail.text = it.content
                binding.textTaskTitleDetail.text = it.title
                binding.textTimeItemTaskDetail.text = DateStringConverter.dateToString(it.date!!)
                binding.taskTagColorDetail.setColorFilter(ContextCompat.getColor(requireContext(),it.tag.resId))
                binding.taskTagDescriptionDetail.text = it.tag.description
            }
        })
    }


}