package vn.edu.hust.studentmanagement

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.Serializable

class MainActivity : AppCompatActivity() {
    lateinit var listStudent: Array<Student>
    lateinit var recyclerView:RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = StudentAdapter(arrayOf<Student>())
    }

    override fun onResume() {
        Log.v("TAG","ỏnesume")
        super.onResume()

        val studentDao = StudentDatabase.getInstance(applicationContext).studentDao()
        lifecycleScope.launch(Dispatchers.IO) {
            listStudent = studentDao.getList()
            withContext(Dispatchers.Main) {
                val adapter = StudentAdapter(listStudent)
                Log.v("TAG","adapter")
                adapter.setOnItemClickListener(object : OnItemClickListener {
                    override fun onItemClick(context: Context, position: Int) {
                        val intent = Intent(context, DetailStudentActivity::class.java)
                        intent.putExtra("student", listStudent.get(position) as Serializable)
                        intent.putExtra("mode", 0) // add student
                        startActivity(intent)
                    }
                })
                recyclerView.adapter = adapter
            }

        }


    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.add_person -> addPerson()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun addPerson() {
        val intent = Intent(this, AddStudentActivity::class.java)
        intent.putExtra("mode", 0)
        startActivity(intent)
    }
}