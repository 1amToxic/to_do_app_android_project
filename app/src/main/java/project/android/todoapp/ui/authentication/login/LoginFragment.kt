package project.android.todoapp.ui.authentication.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import project.android.todoapp.R
import project.android.todoapp.databinding.FragmentLoginBinding
import project.android.todoapp.ui.authentication.AuthenticationActivity
import project.android.todoapp.ui.authentication.AuthenticationNavigatorInstance


class LoginFragment : Fragment() {
    private var _binding : FragmentLoginBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater,container,false)
        setListeners()
        return binding.root
    }

    private fun setListeners() {
        binding.tvRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        binding.btnLoginLogin.setOnClickListener {
            AuthenticationNavigatorInstance.newInstance(requireActivity() as AuthenticationActivity).toMainProgram()
        }
    }

}