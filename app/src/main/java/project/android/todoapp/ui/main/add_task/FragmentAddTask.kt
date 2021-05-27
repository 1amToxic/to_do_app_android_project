package project.android.todoapp.ui.main.add_task


import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import project.android.todoapp.R
import project.android.todoapp.ToDoApplication
import project.android.todoapp.databinding.FragmentAddTaskBinding
import project.android.todoapp.model.Tag
import project.android.todoapp.model.Task
import project.android.todoapp.model.TaskState
import project.android.todoapp.service.NotificationBuilder
import project.android.todoapp.ui.main.add_task.adapter.TagAdapter
import project.android.todoapp.utils.DateStringConverter
import project.android.todoapp.viewmodel.TaskViewModel
import project.android.todoapp.viewmodel.factory.TaskViewModelFactory
import timber.log.Timber
import java.util.*

class FragmentAddTask : Fragment() {
    var textDateTime: String? = ""
    private var _binding: FragmentAddTaskBinding? = null
    val binding: FragmentAddTaskBinding get() = _binding!!
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

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        setListeners()

        setAdapters()
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setListeners() {
        binding.editAddTaskName.requestFocus()
        binding.editAddTaskDate.setOnClickListener {
            showDatePicker()
        }
        binding.fabAddTask.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                val task = Task(
                    0, binding.editAddTaskName.text.toString(),
                    binding.editAddTaskDescription.text.toString(),
                    TaskState.DOING,
                    tag,
                    1,
                    DateStringConverter.stringToDate(binding.editAddTaskDate.text.toString())
                )
                taskViewModel.insertTask(
                    task
                )

                withContext(Dispatchers.Main){
                    findNavController().navigateUp()
                    val notificationBuilder = NotificationBuilder(requireActivity().application)
                    Timber.d("LogTask $task")
                    notificationBuilder.createNotification(requireContext(),task)
                }
            }
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
                binding.editAddTaskDate.setText(textDateTime)
            },
            now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), false
        )
        timePickerDialog.show()
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
                Timber.d(tag.toString())
            }
    }

}
