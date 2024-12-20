package me.cybersteve.kotlin_pr5

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import me.cybersteve.kotlin_pr5.databinding.ActivityMainBinding
import me.cybersteve.kotlin_pr5.model.todoItem.TodoItem
import me.cybersteve.kotlin_pr5.model.todoItem.TodoItemDatabase
import me.cybersteve.kotlin_pr5.retrofit.api.MainApi
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val vm: CustomVM by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startKoin {
            androidContext(this@MainActivity)
            modules(myKoinModule)
        }
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        vm.todoItem.observe(this) {
            binding.todoText.text = vm.getTodoString()
            binding.todoStatus.text = vm.getTodoStatus()
        }
        val retrofit = Retrofit.Builder().baseUrl("https://dummyjson.com")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val todoMainApi = retrofit.create(MainApi::class.java)

        val db = Room.databaseBuilder(applicationContext, TodoItemDatabase::class.java, "todos_db")
            .build()
        val dao = db.todoItemDao()

        binding.button.setOnClickListener() {
            val randId = Random.nextInt(1, 30)

            val result: Deferred<TodoItem> = CoroutineScope(Dispatchers.IO).async {
                val todoItem = todoMainApi.getTodoItemById(randId)
                dao.insertTodoItem(todoItem)
                Log.d("RRR","!!!!!")
                dao.getTodoItemById(randId)
            }

            CoroutineScope(Dispatchers.IO).launch {
                val new_val = result.await()
                runOnUiThread {
                    vm.updateTodoItem(new_val)
                }
            }
        }
    }
}