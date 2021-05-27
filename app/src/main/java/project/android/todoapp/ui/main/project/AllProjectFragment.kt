package project.android.todoapp.ui.main.project

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import project.android.todoapp.R
import project.android.todoapp.ToDoApplication
import project.android.todoapp.databinding.FragmentAllProjectBinding
import project.android.todoapp.ui.main.project.adapter.ProjectAdapter
import project.android.todoapp.viewmodel.ProjectViewModel
import project.android.todoapp.viewmodel.factory.ProjectViewModelFactory

class AllProjectFragment : Fragment() {
    lateinit var binding : FragmentAllProjectBinding
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

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAllProjectBinding.inflate(inflater,container,false)
        setUI()
        return binding.root
    }

    private fun setUI() {
        val adapterProject = ProjectAdapter()
        binding.recyclerProject.apply {
            adapter = adapterProject
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
        projectViewModel.listProject.observe(viewLifecycleOwner, Observer {
            adapterProject.submitList(it)
        })

    }

}