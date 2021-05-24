package project.android.todoapp.ui.authentication.sign_up

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
import project.android.todoapp.databinding.FragmentRegisterBinding
import project.android.todoapp.model.Account
import project.android.todoapp.ui.authentication.AuthenticationActivity
import project.android.todoapp.ui.authentication.AuthenticationNavigatorInstance
import project.android.todoapp.viewmodel.AccountViewModel
import project.android.todoapp.viewmodel.ProjectViewModel
import project.android.todoapp.viewmodel.factory.AccountViewModelFactory
import project.android.todoapp.viewmodel.factory.ProjectViewModelFactory


class RegisterFragment : Fragment() {
    private lateinit var binding : FragmentRegisterBinding
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
        binding = FragmentRegisterBinding.inflate(inflater,container,false)
        setListeners()
        return binding.root
    }

    private fun setListeners() {
        binding.btnRegister.setOnClickListener {
            val account = Account(0,
                binding.edtEmailRegister.text.toString(),
                binding.edtPasswordCfRegister.text.toString())
            GlobalScope.launch(Dispatchers.IO) {
                val accountz = accountViewModel.checkAccountAvailable(account)
                withContext(Dispatchers.Main){
                    if(accountz!=null){
                        Toast.makeText(requireContext(),"This account is exist",Toast.LENGTH_SHORT).show()
                    }else{
                        accountViewModel.insertAccount(account)
                        findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                    }
                }
            }
        }
    }
}