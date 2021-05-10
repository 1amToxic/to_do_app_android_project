package project.android.todoapp.ui.main.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.navigation.NavigationView
import project.android.todoapp.R
import timber.log.Timber

class BottomNavigationDrawerFragment : BottomSheetDialogFragment() {
    private lateinit var navigationView: NavigationView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_bottom_sheet, container, false)
        navigationView = view.findViewById(R.id.bottom_navigation_view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            run {
                when (menuItem.itemId) {
                    R.id.nav_add_project -> {
                        requireActivity().findNavController(R.id.nav_host_fragment_main).navigate(R.id.action_mainFragment_to_fragmentAddTask)

                    }
                    R.id.nav_all_project -> {
                        Timber.d("UI Log All")
                    }
                    R.id.nav_show_timeline -> {
                        Timber.d("UI Log Show")
                    }
                }
                dismiss()
            }
            true
        }
    }
}