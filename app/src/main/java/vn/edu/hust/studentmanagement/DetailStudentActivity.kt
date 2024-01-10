package vn.edu.hust.studentmanagement

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.Serializable

class DetailStudentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_student)

        val student: Student = intent.getSerializableExtra("student") as Student
        val name = findViewById<TextView>(R.id.name)
        val mssv = findViewById<TextView>(R.id.mssv)
        val birth_date = findViewById<TextView>(R.id.birth_date)
        val home_town = findViewById<TextView>(R.id.home_town)
        val email = findViewById<TextView>(R.id.email)
        val edit_btn = findViewById<Button>(R.id.edit_btn)
        val delete_btn = findViewById<Button>(R.id.delete_btn)


        name.text = student.name
        mssv.text = student.mssv
        birth_date.text = student.birthDate
        home_town.text = student.homeTown
        email.text = student.email
        val studentDao = StudentDatabase.getInstance(application).studentDao()
        delete_btn.setOnClickListener{
            lifecycleScope.launch(Dispatchers.IO) {
                studentDao.deleteStudent(mssv.text.toString())
            }
            finish()
        }
        edit_btn.setOnClickListener {
            lifecycleScope.launch (Dispatchers.IO){
                val intent = Intent(this@DetailStudentActivity, AddStudentActivity::class.java)
                intent.putExtra("student", student)
                intent.putExtra("mode", 1) // edit student's info
                startActivity(intent)
            }
            finish()
        }
    }
}