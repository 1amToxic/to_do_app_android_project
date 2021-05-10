package project.android.todoapp.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import project.android.todoapp.R
import project.android.todoapp.databinding.ActivityMainBinding
import project.android.todoapp.ui.behavior.FloatingActionButtonScrollBehavior
import project.android.todoapp.ui.main.detail.BottomNavigationDrawerFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navHostFragment : NavHostFragment
    private lateinit var navController : NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_main) as NavHostFragment
        navController = navHostFragment.navController
        setSupportActionBar(binding.bottomAppBar)
        setNavigation()
        setListeners()
        setBehaviors()
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
                    binding.btnAddTask.visibility = View.VISIBLE
                }
                else ->{
                    binding.bottomAppBar.visibility = View.GONE
                    binding.btnAddTask.visibility = View.GONE
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
    override fun onBackPressed() {
        super.onBackPressed()
    }
}