package project.android.todoapp.repository

import androidx.lifecycle.LiveData
import project.android.todoapp.model.Account
import project.android.todoapp.storage.dao.AccountDao

class AccountRepository(private val accountDao: AccountDao) {
    suspend fun insertAccount(account : Account){
        accountDao.insertAccount(account)
    }
    suspend fun checkAccountAvailable(account: Account) : Account?
        = accountDao.checkAccountAvailable(account.email,account.password)

}