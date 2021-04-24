package project.android.todoapp.viewmodel.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import project.android.todoapp.ToDoApplication
import project.android.todoapp.repository.AccountRepository
import project.android.todoapp.viewmodel.AccountViewModel

class AccountViewModelFactory (val context: Context) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val accountDao = ToDoApplication.todoDb.accountDao()
        val accountRepository = AccountRepository(accountDao)
        return AccountViewModel(accountRepository) as T
    }

}