package com.ucb.mylistview

import TaskAdapter
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import android.media.MediaPlayer


class MainActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var editText: EditText
    private lateinit var addButton: Button
    private lateinit var adapter: TaskAdapter
    private val tasks: MutableList<Task> = mutableListOf()

    private lateinit var mediaPlayer: MediaPlayer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.listView)
        editText = findViewById(R.id.editText)
        addButton = findViewById(R.id.addButton)

        adapter = TaskAdapter(this, tasks)
        listView.adapter = adapter

        mediaPlayer = MediaPlayer.create(this, R.raw.enter_task)


        addButton.setOnClickListener {
            val taskText = editText.text.toString().trim()
            if (taskText.isNotEmpty()) {
                tasks.add(Task(taskText, false))
                adapter.notifyDataSetChanged()
                editText.text.clear()
            } else {
                mediaPlayer.start()
                Toast.makeText(this, "Please enter a task", Toast.LENGTH_SHORT).show()
            }
        }

        listView.setOnItemClickListener { _, _, position, _ ->
            val task = tasks[position]
            Toast.makeText(this, "Task: ${task.text}", Toast.LENGTH_SHORT).show()
        }
    }
    override fun onDestroy() {
        // Release the media player resources when activity is destroyed
        if (::mediaPlayer.isInitialized) {
            mediaPlayer.release()
        }
        super.onDestroy()
    }
}

data class Task(val text: String, var isCompleted: Boolean)