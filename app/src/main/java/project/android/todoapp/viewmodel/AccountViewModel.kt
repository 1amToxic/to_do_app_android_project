package project.android.todoapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import project.android.todoapp.model.Account
import project.android.todoapp.repository.AccountRepository

class AccountViewModel (private val accountRepository: AccountRepository) : ViewModel(){
    private var accountNowM : MutableLiveData<Account>? = null
    val accountNow : LiveData<Account>? get() = accountNowM!!
    fun setAccountNow(account: Account){
        accountNowM?.postValue(account)
    }
    fun insertAccount(account : Account){
        viewModelScope.launch {
            accountRepository.insertAccount(account)
        }
    }
    fun checkAccountAvailable(account: Account) : Account? {
//        return
//        viewModelScope.launch{
          return accountRepository.checkAccountAvailable(account)
//        }
//        return accountNow?.value
    }
}