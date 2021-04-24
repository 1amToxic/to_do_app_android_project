package project.android.todoapp.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView

class CardViewCustom(context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0)
    : CardView(context, attributeSet, defStyleAttr){

    val tagLayout : LinearLayout

    init {
        tagLayout = LinearLayout(context)
        tagLayout.apply {
            addView(TextView(context).apply {
                text = "Alo"
            })
        }

        addView(tagLayout)
    }
}