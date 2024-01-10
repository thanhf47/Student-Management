package vn.edu.hust.studentmanagement

import android.content.Context

interface OnItemClickListener {
    fun onItemClick(context: Context, position: Int)
}