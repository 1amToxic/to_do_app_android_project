package project.android.todoapp.ui.main.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import project.android.todoapp.R
import project.android.todoapp.ToDoApplication
import project.android.todoapp.databinding.FragmentAddProjectBinding
import project.android.todoapp.model.Project
import project.android.todoapp.repository.ProjectRepository
import project.android.todoapp.viewmodel.ProjectViewModel
import project.android.todoapp.viewmodel.TaskViewModel
import project.android.todoapp.viewmodel.factory.ProjectViewModelFactory
import project.android.todoapp.viewmodel.factory.TaskViewModelFactory

class AddProjectDialog : DialogFragment() {
    private lateinit var binding: FragmentAddProjectBinding
    private val projectViewModel: ProjectViewModel by lazy {
        val app = ToDoApplication()
        val viewModelProviderFactory =
            ProjectViewModelFactory(app)
        ViewModelProvider(
            requireActivity(),
            viewModelProviderFactory
        )[ProjectViewModel::class.java]
    }

    companion object {
        const val TAG = "dialog_add_project"
        fun newInstance(): AddProjectDialog {
            return AddProjectDialog()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddProjectBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnCancel.setOnClickListener {
            dismiss()
        }
        binding.btnSubmit.setOnClickListener {
            val project = Project(
                0, 1,
                binding.editAddProjectName.text.toString(),
                binding.editAddTaskDescription.text.toString()
            )
            projectViewModel.insertProject(project)
            dismiss()
        }
    }
}