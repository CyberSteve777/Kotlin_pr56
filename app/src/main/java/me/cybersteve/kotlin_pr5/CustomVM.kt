package me.cybersteve.kotlin_pr5

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.cybersteve.kotlin_pr5.model.todoItem.TodoItem

class CustomVM : ViewModel() {
    private val _todoItem = MutableLiveData<TodoItem>()
    val todoItem: LiveData<TodoItem> = _todoItem

    fun updateTodoItem(newDQuoteTodoItem: TodoItem){
        _todoItem.value = newDQuoteTodoItem
    }

    fun getTodoString(): String {
        return todoItem.value!!.todo
    }

    fun getTodoStatus(): String {
        if (todoItem.value!!.completed) return "Completed"
        return "Not Completed"
    }
}