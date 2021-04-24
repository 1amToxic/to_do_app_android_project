package project.android.todoapp.ui.main.main_screen

import android.graphics.Color
import android.os.Bundle
import android.transition.Slide
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.transition.MaterialContainerTransform
import project.android.todoapp.R
import project.android.todoapp.ToDoApplication
import project.android.todoapp.databinding.FragmentMainBinding
import project.android.todoapp.ui.main.main_screen.adapter.TaskAdapter
import project.android.todoapp.viewmodel.TaskViewModel
import project.android.todoapp.viewmodel.factory.TaskViewModelFactory

class MainFragment : Fragment() {
    private var _binding : FragmentMainBinding? = null
    private val binding get() = _binding!!
    lateinit var adapterTask : TaskAdapter
    private val taskViewModel : TaskViewModel by lazy {
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
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        setListeners()
        setAdapters()
        setUI()
        return binding.root
    }

    private fun setUI() {
        taskViewModel.getAllTask()
        taskViewModel.listAllTask.observe(viewLifecycleOwner, Observer {
            adapterTask.submitList(it)
        })
    }

    private fun setAdapters() {
        adapterTask = TaskAdapter()
        binding.recyclerTask.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = adapterTask
        }
    }

    private fun setListeners() {
        binding.btnAddTask.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_fragmentAddTask)
        }
        binding.bottomAppBar.setNavigationOnClickListener {

        }
//        binding.bottomAppBar.setOnMenuItemClickListener{
//
//        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
    }

}