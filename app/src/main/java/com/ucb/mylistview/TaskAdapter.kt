import android.content.Context
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GestureDetectorCompat
import com.ucb.mylistview.R
import com.ucb.mylistview.Task

class TaskAdapter(private val context: Context, private val tasks: MutableList<Task>) : BaseAdapter() {

    override fun getCount(): Int = tasks.size
    override fun getItem(position: Int): Any = tasks[position]
    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        val task = tasks[position]

        val checkBox: CheckBox = view.findViewById(R.id.checkBox)
        val taskText: TextView = view.findViewById(R.id.taskText)
        val editIcon: ImageView = view.findViewById(R.id.editIcon)

        taskText.text = task.text
        checkBox.isChecked = task.isCompleted

        checkBox.setOnCheckedChangeListener { _, isChecked ->
            task.isCompleted = isChecked
        }

        val gestureDetector = GestureDetectorCompat(context, DoubleTapListener(task, position))

        view.setOnTouchListener { _, event ->
            gestureDetector.onTouchEvent(event)
            true
        }

        return view
    }

    private fun showEditDeleteDialog(task: Task, position: Int) {
        val options = arrayOf("Edit", "Delete")
        AlertDialog.Builder(context)
            .setTitle("Choose Action")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> editTask(task, position)
                    1 -> deleteTask(position)
                }
            }
            .show()
    }

    private fun editTask(task: Task, position: Int) {
        val input = EditText(context)
        input.setText(task.text)

        AlertDialog.Builder(context)
            .setTitle("Edit Task")
            .setView(input)
            .setPositiveButton("Save") { _, _ ->
                val newText = input.text.toString()
                tasks[position] = task.copy(text = newText)
                notifyDataSetChanged()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun deleteTask(position: Int) {
        tasks.removeAt(position)
        notifyDataSetChanged()
    }

    private inner class DoubleTapListener(val task: Task, val position: Int) : GestureDetector.SimpleOnGestureListener() {

        override fun onDoubleTap(e: MotionEvent): Boolean {
            showEditDeleteDialog(task, position)
            return true
        }

        override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
            return false
        }
    }
}
