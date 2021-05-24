package project.android.todoapp.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import project.android.todoapp.model.Account
import project.android.todoapp.storage.dao.AccountDao

class AccountRepository(private val accountDao: AccountDao) {
    suspend fun insertAccount(account : Account){
        accountDao.insertAccount(account)
    }
    fun checkAccountAvailable(account: Account) : Account? =
//        withContext(Dispatchers.IO) {
            accountDao.checkAccountAvailable(account.email, account.password)
//        }

}