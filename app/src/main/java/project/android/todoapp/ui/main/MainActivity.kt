package project.android.todoapp.ui.main

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import project.android.todoapp.R
import project.android.todoapp.ToDoApplication
import project.android.todoapp.databinding.ActivityMainBinding
import project.android.todoapp.model.Account
import project.android.todoapp.model.Project
import project.android.todoapp.model.Task
import project.android.todoapp.storage.dao.remote.RemoteService
import project.android.todoapp.storage.dao.remote.ServiceBuilder
import project.android.todoapp.ui.behavior.FloatingActionButtonScrollBehavior
import project.android.todoapp.ui.main.detail.BottomNavigationDrawerFragment
import project.android.todoapp.viewmodel.ProjectViewModel
import project.android.todoapp.viewmodel.TaskViewModel
import project.android.todoapp.viewmodel.factory.ProjectViewModelFactory
import project.android.todoapp.viewmodel.factory.TaskViewModelFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navHostFragment : NavHostFragment
    private lateinit var navController : NavController
    private val projectViewModel: ProjectViewModel by lazy {
        val app = ToDoApplication()
        val viewModelProviderFactory =
            ProjectViewModelFactory(app)
        ViewModelProvider(
            this,
            viewModelProviderFactory
        )[ProjectViewModel::class.java]
    }
    lateinit var sharedPreference : SharedPreferences
    var projectId : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreference =  getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        projectId = sharedPreference.getInt("projectid",1)
        projectViewModel.getProjectById(projectId)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_main) as NavHostFragment
        navController = navHostFragment.navController
        setBehaviors()
        setSupportActionBar(binding.bottomAppBar)
        setNavigation()
        setListeners()
        loadData()
        callService()
    }

    private fun callService() {
        val request = ServiceBuilder.buildService(RemoteService::class.java)
        GlobalScope.launch(Dispatchers.IO) {
            val call = request.getAllAccount()

            call.enqueue(object : Callback<List<Account>> {
                override fun onResponse(
                    call: Call<List<Account>>,
                    response: Response<List<Account>>
                ) {
                    val listAcc = response.body()
                    Timber.d("This is list Acc $listAcc")
                }

                override fun onFailure(call: Call<List<Account>>, t: Throwable) {
                    Toast.makeText(this@MainActivity,"Can not connect to Internet",Toast.LENGTH_SHORT).show()
                }

            })
        }
        GlobalScope.launch(Dispatchers.IO) {
            val call = request.getAllProject()

            call.enqueue(object : Callback<List<Project>> {
                override fun onResponse(
                    call: Call<List<Project>>,
                    response: Response<List<Project>>
                ) {
                }

                override fun onFailure(call: Call<List<Project>>, t: Throwable) {
                }


            })
        }
        GlobalScope.launch(Dispatchers.IO) {
            val call = request.getAllTaskOfProject(projectId)

            call.enqueue(object : Callback<List<Task>> {
                override fun onResponse(call: Call<List<Task>>, response: Response<List<Task>>) {
                }

                override fun onFailure(call: Call<List<Task>>, t: Throwable) {
                }


            })
        }
    }

    private fun loadData() {
        GlobalScope.launch(Dispatchers.IO) {
            projectViewModel.getAllProject()
        }
    }

    private fun setBehaviors() {
        val p : CoordinatorLayout.LayoutParams = binding.btnAddTask.layoutParams as CoordinatorLayout.LayoutParams
        p.behavior = FloatingActionButtonScrollBehavior(applicationContext)
        binding.btnAddTask.layoutParams = p
    }

    private fun setListeners() {
        binding.btnAddTask.setOnClickListener {
            navController.navigate(R.id.action_mainFragment_to_fragmentAddTask)
        }
    }

    private fun setNavigation() {
        navController.addOnDestinationChangedListener{
            _,destination, _ ->
            when(destination.id){
                R.id.mainFragment ->{
                    binding.bottomAppBar.visibility = View.VISIBLE
                    binding.btnAddTask.show()
                }
                else ->{
                    binding.bottomAppBar.visibility = View.GONE
                    binding.btnAddTask.hide()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.bottom_app_bar,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->{
                val bottomNavDrawerFragment = BottomNavigationDrawerFragment()
                bottomNavDrawerFragment.show(supportFragmentManager, bottomNavDrawerFragment.tag)
            }
        }
        return true
    }

}