package project.android.todoapp.ui.main.main_screen

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import project.android.todoapp.R
import project.android.todoapp.ToDoApplication
import project.android.todoapp.databinding.FragmentMainBinding
import project.android.todoapp.model.Task
import project.android.todoapp.ui.behavior.FloatingActionButtonScrollBehavior
import project.android.todoapp.ui.main.main_screen.adapter.SwipeToDeleteCallback
import project.android.todoapp.ui.main.main_screen.adapter.TaskAdapter
import project.android.todoapp.ui.main.main_screen.model.TaskUI
import project.android.todoapp.viewmodel.ProjectViewModel
import project.android.todoapp.viewmodel.TaskViewModel
import project.android.todoapp.viewmodel.factory.ProjectViewModelFactory
import project.android.todoapp.viewmodel.factory.TaskViewModelFactory
import timber.log.Timber

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    lateinit var adapterTask: TaskAdapter
    var projectId : Int = 0
    private val taskViewModel: TaskViewModel by lazy {
        val app = ToDoApplication()
        val viewModelProviderFactory =
            TaskViewModelFactory(app)
        ViewModelProvider(
            requireActivity(),
            viewModelProviderFactory
        )[TaskViewModel::class.java]
    }
    private val projectViewModel: ProjectViewModel by lazy {
        val app = ToDoApplication()
        val viewModelProviderFactory =
            ProjectViewModelFactory(app)
        ViewModelProvider(
            requireActivity(),
            viewModelProviderFactory
        )[ProjectViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPreference = requireActivity().getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        projectId = sharedPreference.getInt("projectid",1)


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
    }

    private fun setUI() {
        projectViewModel.projectNow.observe(viewLifecycleOwner, Observer {
            binding.textProjectName.text = it.name
        })
        GlobalScope.launch(Dispatchers.IO) {
            taskViewModel.getAllTaskOfProject(projectId)
            withContext(Dispatchers.Main){
                taskViewModel.listDisplay.observe(viewLifecycleOwner, Observer {
                    Timber.d("Update View")
                    adapterTask.submitList(it)
                })
                taskViewModel.numberTaskComplete.observe(viewLifecycleOwner, Observer {
                    Timber.d("Task Done $it")
                    binding.linearProgressBar.progress = it
                    binding.textPercentComplete.text = "$it%"
                })
            }
        }

    }

    private fun setAdapters() {
        adapterTask = TaskAdapter(
            {task1 -> updateTaskClick(task1)},
            {task2 -> updateTaskChange(task2)} ,
            {task3 -> deleteTask(task3)}
        )
        binding.recyclerTask.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = adapterTask
            val swipeHandler = object : SwipeToDeleteCallback(context) {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    AlertDialog.Builder(requireContext())
                        .setTitle("ToDoForDev")
                        .setMessage("Do you want to delete this task?")
                        .setPositiveButton("Yes"
                        ) { _, _ ->
                            val adapter = this@apply.adapter as TaskAdapter
                            adapter.deleteItem(viewHolder.adapterPosition)
                        }
                        .setNegativeButton("No"){
                            _,_ ->
                        }
                        .show()

                }
            }
            val itemTouchHelper = ItemTouchHelper(swipeHandler)
            itemTouchHelper.attachToRecyclerView(binding.recyclerTask)
        }
    }
    private fun updateTaskChange(task : Task) {
        taskViewModel.updateTask(task)
        adapterTask.notifyDataSetChanged()
    }
    private fun deleteTask(task : Task) {
        taskViewModel.deleteTask(task)
    }

    private fun updateTaskClick(taskUI : Task){
        taskViewModel.setTaskDetailDisplay(taskUI)
        findNavController().navigate(R.id.action_mainFragment_to_detailFragment)
    }

    private fun setListeners() {
    }



}