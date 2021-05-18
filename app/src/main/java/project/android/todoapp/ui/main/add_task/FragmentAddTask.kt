package project.android.todoapp.ui.main.add_task


import android.app.DatePickerDialog
import android.app.TimePickerDialog
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
import timber.log.Timber
import java.util.*

class FragmentAddTask : Fragment() {
    var textDateTime : String? = ""
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
        setListeners()

        setAdapters()
        return binding.root
    }

    private fun setListeners() {
        binding.editAddTaskName.isFocusable = true
        binding.editAddTaskDate.setOnClickListener{
            showDatePicker()
        }
    }

    private fun showDatePicker() {
        val now = Calendar.getInstance()
        Timber.d("DateTimeHere $now")
        val datePickerDialog = DatePickerDialog(requireContext(),
            { _, year, month, dayOfMonth ->
                textDateTime = "$year-$month-$dayOfMonth"
                showTimePiker()
            },
            now.get(Calendar.YEAR),now.get(Calendar.MONTH)+1,now.get(Calendar.DAY_OF_MONTH))
        datePickerDialog.show()
    }
    private fun showTimePiker(){
        val now = Calendar.getInstance()
        val timePickerDialog = TimePickerDialog(requireContext(),
            { _, hourOfDay, minute ->
                textDateTime = "$textDateTime $hourOfDay:$minute"
                binding.editAddTaskDate.setText(textDateTime)
            },
            now.get(Calendar.HOUR_OF_DAY),now.get(Calendar.MINUTE),false)
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
            }
    }



}
