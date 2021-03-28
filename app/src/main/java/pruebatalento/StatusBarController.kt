package pruebatalento

import android.app.Activity
import android.view.View
import android.view.WindowManager
import androidx.core.content.res.ResourcesCompat
import com.pruebatalento.R

class StatusBarController {

    //Hide statusBar with time, battery, etc
    fun fullScreen(activity: Activity) {
        activity.window?.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }
    //Show statusBar with time, battery, etc - With different color
    fun translucentScreen(activity: Activity) {
        activity.window?.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        activity.window.statusBarColor = ResourcesCompat.getColor(activity.baseContext.resources, R.color.colorStatusBar, null)
    }

    fun hideStatusBar(activity: Activity) {
        activity.actionBar?.hide()
    }
}