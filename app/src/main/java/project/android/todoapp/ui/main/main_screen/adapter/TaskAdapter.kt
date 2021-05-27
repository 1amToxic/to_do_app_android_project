package project.android.todoapp.ui.main.main_screen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import project.android.todoapp.R
import project.android.todoapp.ToDoApplication
import project.android.todoapp.databinding.ItemTaskBinding
import project.android.todoapp.model.Task
import project.android.todoapp.model.TaskState
import project.android.todoapp.ui.main.main_screen.model.TaskUI
import project.android.todoapp.utils.DateStringConverter
import project.android.todoapp.viewmodel.TaskViewModel
import project.android.todoapp.viewmodel.factory.TaskViewModelFactory
import timber.log.Timber

class TaskAdapter(
    private val onClickItem: (Task) -> Unit,
    private val onItemChange: (Task) -> Unit,
    private val onDeleteItem: (Task) -> Unit
) :
    ListAdapter<Task, TaskAdapter.TaskViewHolder>(DiffUtilCallback()) {
    private lateinit var binding: ItemTaskBinding

    inner class TaskViewHolder(private val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(taskUI: Task) {
            itemView.post {
                setUpIconState(taskUI)
                binding.textTaskTitle.text = taskUI.title
                binding.textContentItemTask.text = taskUI.description
                taskUI.deadline?.let {
                    binding.textTimeItemTask.text = DateStringConverter.dateToString(it)
                }
                binding.taskTagColor.setColorFilter(
                    ContextCompat.getColor(
                        binding.root.context,
                        taskUI.tag.resId
                    )
                )
                binding.taskTagDescription.text = taskUI.tag.description
            }
            itemView.setOnClickListener {
                catchListenerOfItem(taskUI)
            }
            binding.iconState.setOnClickListener {
                if (taskUI.state == TaskState.DOING) {
                    taskUI.state = TaskState.DONE
                } else {
                    taskUI.state = TaskState.DOING
                }
                catchListenerChange(taskUI)
                setUpIconState(taskUI)
            }

        }

        private fun setUpIconState(taskUI: Task) {
            GlobalScope.launch(Dispatchers.Main) {
                if (taskUI.state == TaskState.DOING) {
                    binding.iconState.setImageResource(R.drawable.check)
                } else {
                    binding.iconState.setImageResource(R.drawable.reload)
                }
            }
        }
    }

    fun deleteItem(position: Int) {
        onDeleteItem(getItem(position))
        notifyItemRemoved(position)
    }

    fun catchListenerChange(task: Task) {
        onItemChange(task)
    }

    fun catchListenerOfItem(task: Task) {
        onClickItem(task)
    }

    class DiffUtilCallback : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
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