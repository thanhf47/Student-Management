package vn.edu.hust.studentmanagement

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.room.util.newStringBuilder
import kotlinx.coroutines.launch

class AddStudentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)

        val mode = intent.getIntExtra("mode", -1)
        Log.v("TAG","MOde: $mode")
        val mssv = findViewById<EditText>(R.id.mssv)
        val name = findViewById<EditText>(R.id.name)
        val birth_date = findViewById<TextView>(R.id.birth_date)
        val calendar_btn = findViewById<ImageButton>(R.id.calendar_btn)
        val home_town = findViewById<AutoCompleteTextView>(R.id.home_town)
        val add_btn = findViewById<Button>(R.id.add_btn)

        val inputStream = resources.openRawResource(R.raw.province)
        val reader = inputStream.reader()
        val content = reader.readText()
        reader.close()

        val list = content.split(",").map { it.trim() }

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)
        home_town.setAdapter(adapter)

        val studentDao = StudentDatabase.getInstance(application).studentDao()
        calendar_btn.setOnClickListener {
            DatePickerDialog(this, DatePickerDialog.OnDateSetListener{ datePicker, i, i2, i3 ->
                birth_date.text = "$i3/${i2+1}/$i"}, 2003, 7, 4).show()
        }

        if (mode == 1) {
            add_btn.setText("Xác nhận")
            val student = intent.getSerializableExtra("student") as Student
            mssv.setText(student.mssv)
            name.setText(student.name)
            birth_date.text = student.birthDate
            home_town.setText(student.homeTown)
        }

        add_btn.setOnClickListener{
            if (mssv.text.isEmpty()) {
                Toast.makeText(this, "Vui lòng điền mã số sinh viên", Toast.LENGTH_SHORT).show()
            }
            else if (name.text.isEmpty()) {
                Toast.makeText(this, "Vui lòng điền họ và tên", Toast.LENGTH_SHORT).show()
            }
            else if (birth_date.text.isEmpty()) {
                Toast.makeText(this, "Vui lòng điền ngày sinh", Toast.LENGTH_SHORT).show()
            }
            else if (home_town.text.isEmpty()) {
                Toast.makeText(this, "Vui lòng điền quê quán", Toast.LENGTH_SHORT).show()
            }
            else {
                val email = generateEmail(name.text.toString(), mssv.text.toString())
                val newStudent = Student(mssv.text.toString(), name.text.toString(), birth_date.text.toString(), home_town.text.toString(), email)
                lifecycleScope.launch {
                    if (mode == 0) {
                        studentDao.insertStudent(newStudent)
                        Log.v("TAG","ÍNerted")
                    }
                    else if (mode == 1) {
                        studentDao.updateStudent(newStudent)
                    }
                }
                // dong AddStudentActivity, quay ve MainActivity
                finish()
            }
        }
    }

    private fun generateEmail(name: String, mssv: String): String {
        val formatName = name.toUpperCase()
        val parts = formatName.split(" ")
        var res = ""
        for (i in 0..parts.size - 2) {
            res += parts.get(i).get(0)
        }
        res = parts.get(parts.size - 1) + "." + res  + mssv.substring(2) + "sis.hust.edu.vn"

        return res
    }
}