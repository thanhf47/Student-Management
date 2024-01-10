package vn.edu.hust.studentmanagement

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "students")
data class Student (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "mssv")
    val mssv: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "birth")
    val birthDate: String,

    @ColumnInfo(name = "home_town")
    val homeTown: String,

    @ColumnInfo(name = "email")
    val email: String,
) : Serializable