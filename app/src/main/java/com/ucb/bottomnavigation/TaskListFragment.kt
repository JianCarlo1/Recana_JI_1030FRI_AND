package com.ucb.bottomnavigation

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import android.util.Log
import android.os.Handler

class TaskListFragment : Fragment() {

    private lateinit var listView: ListView
    private lateinit var editText: EditText
    private lateinit var addButton: Button
    private val tasks = mutableListOf<String>()
    private lateinit var adapter: ArrayAdapter<String>

    private var lastClickTime: Long = 0
    private val doubleClickDelay: Long = 300 // milliseconds

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_task_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        listView = view.findViewById(R.id.listView)
        editText = view.findViewById(R.id.editText)
        addButton = view.findViewById(R.id.addButton)

        Log.d("TaskListFragment", "Views initialized")

        adapter = object : ArrayAdapter<String>(requireContext(), R.layout.list_item_task, R.id.taskTextView, tasks) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent)

                val taskTextView = view.findViewById<TextView>(R.id.taskTextView)
                val editIcon = view.findViewById<ImageView>(R.id.editIcon)

                taskTextView.text = tasks[position]

                // Handle double click to edit
                taskTextView.setOnClickListener {
                    val currentTime = System.currentTimeMillis()
                    if (currentTime - lastClickTime < doubleClickDelay) {
                        showEditDialog(position)
                    }
                    lastClickTime = currentTime
                }

                editIcon.setOnClickListener {
                    showEditDialog(position)
                }

                return view
            }
        }

        listView.adapter = adapter

        addButton.setOnClickListener {
            Log.d("TaskListFragment", "Add Button clicked")
            val task = editText.text.toString().trim() // Trim whitespace
            if (task.isNotEmpty()) {
                tasks.add(task)
                adapter.notifyDataSetChanged()
                editText.text.clear()
                Log.d("TaskListFragment", "Task added: $task")
            } else {
                Toast.makeText(requireContext(), "Please enter a task", Toast.LENGTH_SHORT).show()
                Log.d("TaskListFragment", "No task entered")
            }
        }
    }

    private fun showEditDialog(position: Int) {
        val taskToEdit = tasks[position]
        val editText = EditText(requireContext()).apply {
            setText(taskToEdit)
        }

        AlertDialog.Builder(requireContext())
            .setTitle("Edit Task")
            .setView(editText)
            .setPositiveButton("Save") { _, _ ->
                val newTask = editText.text.toString().trim()
                if (newTask.isNotEmpty()) {
                    tasks[position] = newTask // Update the task
                    adapter.notifyDataSetChanged()
                    Log.d("TaskListFragment", "Task updated: $newTask")
                } else {
                    Toast.makeText(requireContext(), "Task cannot be empty", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Delete") { _, _ ->
                tasks.removeAt(position) // Remove the task
                adapter.notifyDataSetChanged()
                Log.d("TaskListFragment", "Task deleted")
            }
            .setNeutralButton("Cancel", null)
            .show()
    }
}
