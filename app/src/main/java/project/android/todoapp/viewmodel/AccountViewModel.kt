package project.android.todoapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import project.android.todoapp.model.Account
import project.android.todoapp.repository.AccountRepository

class AccountViewModel (private val accountRepository: AccountRepository) : ViewModel(){
    fun insertAccount(account : Account){
        viewModelScope.launch {
            accountRepository.insertAccount(account)
        }
    }
    fun checkAccountAvailable(account: Account) : Account?{
        var accountz : Account? = null
        viewModelScope.launch {
            accountz = accountRepository.checkAccountAvailable(account)
        }
        return accountz
    }
}