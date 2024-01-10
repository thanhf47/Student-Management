package vn.edu.hust.studentmanagement

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter(private val students: Array<Student>) : RecyclerView.Adapter<StudentAdapter.MyViewHolder>() {

    private lateinit var myListener: OnItemClickListener
    fun setOnItemClickListener(listener: OnItemClickListener) {
        myListener = listener
    }


    class MyViewHolder(view: View, listener: OnItemClickListener) : RecyclerView.ViewHolder(view) {
        val mssv = view.findViewById<TextView>(R.id.mssv)
        val name = view.findViewById<TextView>(R.id.name)
        val email = view.findViewById<TextView>(R.id.email)

        init {
            view.setOnClickListener {
                listener.onItemClick(view.context, adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        return MyViewHolder(view, myListener)
    }

    override fun getItemCount(): Int {
        return students.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val s = students[position]
        holder.mssv.text = s.mssv
        holder.name.text = s.name
        holder.email.text = s.email
    }
}