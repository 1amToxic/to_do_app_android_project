package project.android.todoapp.ui.authentication.greeting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import project.android.todoapp.R
import project.android.todoapp.databinding.FragmentGreetingBinding


class GreetingFragment : Fragment() {
    private var _binding : FragmentGreetingBinding? = null
    private val binding  get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentGreetingBinding.inflate(inflater,container,false)
        setListener()
        return binding.root
    }
    private fun setListener(){
        binding.btnLoginGreeting.setOnClickListener {
            findNavController().navigate(R.id.action_greetingFragment_to_loginFragment)
        }
        binding.btnRegisterGreeting.setOnClickListener {
            findNavController().navigate(R.id.action_greetingFragment_to_registerFragment)
        }
    }

}