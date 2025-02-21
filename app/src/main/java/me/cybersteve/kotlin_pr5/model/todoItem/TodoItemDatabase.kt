package me.cybersteve.kotlin_pr5.model.todoItem

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [TodoItem::class], version = 1)
abstract class TodoItemDatabase : RoomDatabase() {
    abstract fun todoItemDao(): TodoItemDao
}