package vn.edu.hust.studentmanagement

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface StudentDao {

    @Query("Select * from students")
    suspend fun getList() : Array<Student>

    @Query("Select * from students where mssv like :mssv")
    suspend fun getStudent(mssv: Int): Student

    @Insert
    suspend fun insertStudent(student: Student): Long

    @Update
    suspend fun updateStudent(student: Student): Int

    @Query("Delete from students where mssv = :mssv")
    suspend fun deleteStudent(mssv: String)
}