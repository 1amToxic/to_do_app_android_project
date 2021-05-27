package project.android.todoapp.ui.main.edit_task

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import project.android.todoapp.R
import project.android.todoapp.ToDoApplication
import project.android.todoapp.databinding.FragmentEditTaskBinding
import project.android.todoapp.model.Tag
import project.android.todoapp.model.Task
import project.android.todoapp.storage.dao.remote.RemoteService
import project.android.todoapp.storage.dao.remote.ServiceBuilder
import project.android.todoapp.ui.main.add_task.adapter.TagAdapter
import project.android.todoapp.ui.main.main_screen.model.TaskUI
import project.android.todoapp.utils.DateStringConverter
import project.android.todoapp.viewmodel.TaskViewModel
import project.android.todoapp.viewmodel.factory.TaskViewModelFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.util.*


class EditTaskFragment : Fragment() {
    private lateinit var binding: FragmentEditTaskBinding
    private var tagEdit = Tag.OTHER
    private var textDateTime: String? = ""
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
        binding = FragmentEditTaskBinding.inflate(inflater, container, false)
        setListeners()
        initView()
        return binding.root
    }

    private fun setListeners() {
        binding.editTaskName.requestFocus()
        binding.editTaskDate.setOnClickListener {
            showDatePicker()
        }
        binding.fabEditTaskDone.setOnClickListener {
            showAlertDialog()

        }
    }

    private fun showAlertDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Confirm Message")
            .setMessage("Do you want to save this task?")
            .setPositiveButton(
                resources.getString(R.string.Yes)
            ) { _, _ ->
                updateData()
                findNavController().navigateUp()
            }
            .setNegativeButton(resources.getString(R.string.No)) { _, _ ->
                /**
                 * Dismiss Alert Dialog
                 */
            }
            .create()
            .show()
    }

    private fun updateData() {
        val taskEdit = Task(
            taskViewModel.taskDetailDisplay.value!!.id,
            binding.editTaskName.text.toString(),
            binding.editTaskDescription.text.toString(),
            taskViewModel.taskDetailDisplay.value!!.state,
            tagEdit,
            taskViewModel.taskDetailDisplay.value!!.projectId,
            DateStringConverter.stringToDate(binding.editTaskDate.text.toString())
        )
        taskViewModel.updateTask(
            taskEdit
        )
        callService(taskEdit)
        taskViewModel.setTaskDetailDisplay(taskEdit)
    }

    private fun callService(taskEdit: Task) {
        val request = ServiceBuilder.buildService(RemoteService::class.java)
        GlobalScope.launch(Dispatchers.IO) {
            val call = request.editTask(taskEdit.id,taskEdit)

            call.enqueue(object : Callback<Task> {
                override fun onResponse(call: Call<Task>, response: Response<Task>) {
                    Timber.d("Push Good")
                }

                override fun onFailure(call: Call<Task>, t: Throwable) {
                    Timber.d("Push Failed")
                }


            })
        }
    }


    private fun showDatePicker() {
        val now = Calendar.getInstance()
        Timber.d("DateTimeHere $now")
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                textDateTime = "$year-$month-$dayOfMonth"
                showTimePiker()
            },
            now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1, now.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    private fun showTimePiker() {
        val now = Calendar.getInstance()
        val timePickerDialog = TimePickerDialog(
            requireContext(),
            { _, hourOfDay, minute ->
                textDateTime = "$textDateTime $hourOfDay:$minute"
                binding.editTaskDate.setText(textDateTime)
            },
            now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), false
        )
        timePickerDialog.show()
    }

    private fun initView() {
        setAdaptersDropDown()
        taskViewModel.taskDetailDisplay.observe(viewLifecycleOwner, Observer {
            it.let {
                binding.editTaskName.setText(it.title)
                binding.editTaskDescription.setText(it.description)
                binding.editTaskDate.setText(DateStringConverter.dateToString(it.deadline!!))
                binding.layoutEditTaskTag.hint = it.tag.description
            }
        })

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
                tagEdit = enumValues<Tag>()[position]
                binding.layoutEditTaskTag.hint = resources.getString(R.string.tag)
            }
    }

}