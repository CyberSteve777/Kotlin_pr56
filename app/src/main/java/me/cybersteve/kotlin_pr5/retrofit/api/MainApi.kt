package me.cybersteve.kotlin_pr5.retrofit.api

import me.cybersteve.kotlin_pr5.model.todoItem.TodoItem
import retrofit2.http.GET
import retrofit2.http.Path

interface MainApi {
    @GET("https://dummyjson.com/todos/{id}")
    suspend fun getTodoItemById(@Path("id") id: Int): TodoItem
}