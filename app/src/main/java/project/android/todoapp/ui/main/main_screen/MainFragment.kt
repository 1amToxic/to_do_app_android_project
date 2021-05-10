package project.android.todoapp.ui.main.main_screen

import android.os.Bundle
import android.view.*
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import project.android.todoapp.R
import project.android.todoapp.ToDoApplication
import project.android.todoapp.databinding.FragmentMainBinding
import project.android.todoapp.ui.behavior.FloatingActionButtonScrollBehavior
import project.android.todoapp.ui.main.main_screen.adapter.TaskAdapter
import project.android.todoapp.ui.main.main_screen.model.TaskUI
import project.android.todoapp.viewmodel.TaskViewModel
import project.android.todoapp.viewmodel.factory.TaskViewModelFactory
import timber.log.Timber

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    lateinit var adapterTask: TaskAdapter
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
        Timber.d("OnCreateView")
        // Inflate the layout for this fragment
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        setListeners()
        setAdapters()
        setUI()
        setBehaviors()
        return binding.root
    }

    private fun setBehaviors() {
//        val p : CoordinatorLayout.LayoutParams = binding.btnAddTask.layoutParams as CoordinatorLayout.LayoutParams
//        p.behavior = context?.let { FloatingActionButtonScrollBehavior(it) }
//        binding.btnAddTask.layoutParams = p
    }

    private fun setUI() {
        taskViewModel.getAllTask()
        taskViewModel.listAllTask.observe(viewLifecycleOwner, Observer {
            Timber.d("Update View")
            adapterTask.submitList(it)
        })
    }

    private fun setAdapters() {
        adapterTask = TaskAdapter{
            updateTaskClick(it)
        }
        binding.recyclerTask.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = adapterTask
        }
    }

    private fun updateTaskClick(taskUI : TaskUI){
        taskViewModel.setTaskDetailDisplay(taskUI)
        findNavController().navigate(R.id.action_mainFragment_to_detailFragment)
    }

    private fun setListeners() {
//        binding.btnAddTask.setOnClickListener {
//            findNavController().navigate(R.id.action_mainFragment_to_fragmentAddTask)
//        }
//        binding.bottomAppBar.setNavigationOnClickListener {
//
//        }
//        binding.bottomAppBar.setOnMenuItemClickListener{
//
//        }
    }



}