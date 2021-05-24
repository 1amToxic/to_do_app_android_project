package project.android.todoapp.ui.main.main_screen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import project.android.todoapp.databinding.ItemTaskBinding
import project.android.todoapp.ui.main.main_screen.model.TaskUI
import project.android.todoapp.utils.DateStringConverter
import timber.log.Timber

class TaskAdapter(private val onClickItem: (TaskUI) -> Unit) :
    ListAdapter<TaskUI, TaskAdapter.TaskViewHolder>(DiffUtilCallback()) {
    private lateinit var binding: ItemTaskBinding

    inner class TaskViewHolder(private val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(taskUI: TaskUI) {
            itemView.post {
                binding.textTaskTitle.text = taskUI.title
                binding.textContentItemTask.text = taskUI.content
                taskUI.date?.let {
                    binding.textTimeItemTask.text = DateStringConverter.dateToString(it)
                }
                Timber.d("Logcat ${taskUI.tag}")
                binding.taskTagColor.setColorFilter(ContextCompat.getColor(binding.root.context, taskUI.tag.resId))
                binding.taskTagDescription.text = taskUI.tag.description
            }
            itemView.setOnClickListener {
                catchListenerOfItem(taskUI)
            }
        }
    }

    fun catchListenerOfItem(taskUI: TaskUI) {
        onClickItem(taskUI)
    }

    class DiffUtilCallback : DiffUtil.ItemCallback<TaskUI>() {
        override fun areItemsTheSame(oldItem: TaskUI, newItem: TaskUI): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TaskUI, newItem: TaskUI): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}