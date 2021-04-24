package project.android.todoapp.ui.authentication

import android.app.ActivityOptions
import android.content.Intent
import project.android.todoapp.ui.main.MainActivity
object AuthenticationNavigatorInstance {
    private var authenticationNavigator : AuthenticationNavigator? = null
    fun newInstance(activity: AuthenticationActivity) : AuthenticationNavigator {
        if(authenticationNavigator == null){
            authenticationNavigator = AuthenticationNavigator(activity)
        }
        return authenticationNavigator!!
    }
}

class AuthenticationNavigator(private val activity: AuthenticationActivity) {
    fun toMainProgram() {
        activity.startActivity(Intent(activity, MainActivity::class.java),
            ActivityOptions.makeSceneTransitionAnimation(activity).toBundle())
    }
}