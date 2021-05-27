package project.android.todoapp.model

import androidx.annotation.ColorRes
import project.android.todoapp.R

enum class Tag(
    val description: String,
    @ColorRes val resId: Int
) {
    DB("Persistence", R.color.pink),
    UI("UI/UX", R.color.red),
    AR_VR("AR/VR", R.color.indigo),
    ARCH("Architecture", R.color.blue),
    WEB("Web", R.color.blue500),
    CAM("School", R.color.teal),
    MAPS("Photoshop", R.color.green),
    ML("Machine Learning & CV", R.color.lime),
    IOT("IoT", R.color.yellow),
    JETPACK("Server", R.color.amber),
    MEDIA("Backend", R.color.orange),
    KOTLIN_NATIVE("Kotlin/Native", R.color.deep_orange400),
    KOTLIN_JS("Kotlin/JS", R.color.deep_orange400),
    OTHER("Other", R.color.brown);

    override fun toString(): String {
        return description
    }
}