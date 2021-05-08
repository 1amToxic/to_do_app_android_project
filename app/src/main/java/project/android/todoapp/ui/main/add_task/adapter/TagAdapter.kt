package project.android.todoapp.ui.main.add_task.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import project.android.todoapp.R
import project.android.todoapp.model.Tag

class TagAdapter(context: Context, resource: Int, private val items: Array<out Tag>) :
    ArrayAdapter<Tag>(context, resource, items) {

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tag_dropdown, parent, false)
        val imageView = view.findViewById<ImageView>(R.id.task_tag_color)
        imageView.setColorFilter(ContextCompat.getColor(context, items[position].resId))
        val textView = view.findViewById<TextView>(R.id.task_tag_description)
        textView.text = items[position].description
        return view
    }
}