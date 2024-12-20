package me.cybersteve.kotlin_pr5.model.todoItem

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TodoItemDao {
    @Query("select * from todos")
    fun getAllTodoItems(): LiveData<List<TodoItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTodoItem(vararg todoItem: TodoItem)

    @Query("select * from todos where id = :id ")
    fun getTodoItemById(id: Int): TodoItem

    @Update
    fun updateTodoItem(todoItem: TodoItem)

    @Delete
    fun deleteTodoItem(todoItem: TodoItem)
}