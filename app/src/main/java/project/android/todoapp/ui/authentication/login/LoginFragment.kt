package project.android.todoapp.ui.authentication.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import project.android.todoapp.R
import project.android.todoapp.ToDoApplication
import project.android.todoapp.databinding.FragmentLoginBinding
import project.android.todoapp.model.Account
import project.android.todoapp.ui.authentication.AuthenticationActivity
import project.android.todoapp.ui.authentication.AuthenticationNavigatorInstance
import project.android.todoapp.viewmodel.AccountViewModel
import project.android.todoapp.viewmodel.factory.AccountViewModelFactory


class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val accountViewModel: AccountViewModel by lazy {
        val app = ToDoApplication()
        val viewModelProviderFactory =
            AccountViewModelFactory(app)
        ViewModelProvider(
            requireActivity(),
            viewModelProviderFactory
        )[AccountViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        setListeners()
        return binding.root
    }

    private fun setListeners() {
        binding.tvRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        binding.btnLoginLogin.setOnClickListener {
            val account = Account(
                0,
                binding.edtEmailLogin.text.toString(),
                binding.edtPasswordLogin.text.toString()
            )
            GlobalScope.launch(Dispatchers.IO) {
                val accountz = accountViewModel.checkAccountAvailable(account)
                withContext(Dispatchers.Main){
                    if(accountz!=null){
                        AuthenticationNavigatorInstance.newInstance(requireActivity() as AuthenticationActivity)
                            .toMainProgram()
                    }else{
                        Toast.makeText(requireContext(), "Email or password is invalid", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }

}