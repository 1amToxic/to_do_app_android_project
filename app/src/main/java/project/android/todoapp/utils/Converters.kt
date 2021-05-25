package project.android.todoapp.utils

import androidx.room.TypeConverter
import project.android.todoapp.model.Tag
import project.android.todoapp.model.TaskState
import java.util.*

object Converters {

    @TypeConverter
    fun toTag(value: String) = enumValueOf<Tag>(value)

    @TypeConverter
    fun fromTag(value: Tag) = value.name

    @TypeConverter
    fun toTaskState(value: String) = enumValueOf<TaskState>(value)

    @TypeConverter
    fun fromTaskState(value: TaskState) = value.name

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }
}