package com.example.swapyapp.ui.fragments.addpost

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.swapyapp.MainActivity
import com.example.swapyapp.R
import com.example.swapyapp.ui.fragments.feed.FeedFragment
import java.text.SimpleDateFormat
import java.util.*

class AddPostFragment : Fragment() {

    private lateinit var addPostViewModel: AddPostViewModel

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        addPostViewModel =
            ViewModelProviders.of(this).get(AddPostViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_add_post, container, false)


        val viewManager = LinearLayoutManager(context)
        val viewAdapter = AddPostRecyclerAdapter()

        recyclerView = root.findViewById<RecyclerView>(R.id.add_post_main_recycler).apply {
            setHasFixedSize(true)

            layoutManager = viewManager

            adapter = viewAdapter
        }


        return root
    }


    class AddPostRecyclerAdapter :
    RecyclerView.Adapter<AddPostRecyclerAdapter.MyViewHolder>() {
        private val TYPE_HEADER = 0
        private val TYPE_BODY = 1

        private lateinit var recyclerView: RecyclerView

//        var post:UploadingPost = UploadingPost()

        open class MyViewHolder(val row: View) : RecyclerView.ViewHolder(row)

        override fun onCreateViewHolder(parent: ViewGroup,
                                        viewType: Int): MyViewHolder {
            return if (viewType == TYPE_HEADER) {
                VHHeader(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.add_post_header, parent, false) as View
                )
            }else {
                VHItem(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.add_post_body, parent, false) as View
                )
            }
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

            if (holder is VHItem) {


                val comment:EditText = holder.row.findViewById(R.id.text_add_post_comment)
                comment.setText(UploadingPost.INSTANCE.comment)


                comment.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable) {}
                    override fun beforeTextChanged(
                        s: CharSequence, start: Int,
                        count: Int, after: Int
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence, start: Int,
                        before: Int, count: Int
                    ) {
                        UploadingPost.INSTANCE.comment = comment.text.toString()
                    }
                })


                val viewManager = LinearLayoutManager(MainActivity.MAIN_ACTIVITY)
                val viewAdapter = AddFileRecyclerAdapter()

                recyclerView = holder.row.findViewById<RecyclerView>(R.id.add_post_body_recycler_view).apply {
                    setHasFixedSize(true)

                    layoutManager = viewManager

                    adapter = viewAdapter
                }


                val uploadButton = holder.row.findViewById<Button>(R.id.button_add_post_upload)

                uploadButton.setOnClickListener(object : View.OnClickListener {
                    override fun onClick(v: View?) {
                        addFileRow("MarcSQL 1.16 RUSMARC1.zip")
                    }

                })



            }
//            else if (holder is VHHeader) {
//
//            }
        }

        fun addFileRow(fileName:String){
            val viewAdapter: AddFileRecyclerAdapter = recyclerView.adapter as AddFileRecyclerAdapter
            val files = UploadingPost.INSTANCE.files

            files.add(fileName)

            viewAdapter.notifyItemInserted(files.size-1)

            recyclerView.requestLayout()
        }


        override fun getItemCount(): Int {
            return 2
        }

        override fun getItemViewType(position: Int): Int {
            return if (isPositionHeader(position)) TYPE_HEADER else TYPE_BODY
        }

        private fun isPositionHeader(position: Int): Boolean {
            return position == 0
        }

//        private fun getItem(position: Int): String? {
//            return myDataset[position - 1]
//        }

        internal class VHItem(itemView: View) :
            MyViewHolder(itemView)

        internal class VHHeader(itemView: View) :
            MyViewHolder(itemView)
    }




    class AddFileRecyclerAdapter :
        RecyclerView.Adapter<AddFileRecyclerAdapter.MyViewHolder>() {

        open class MyViewHolder(val row: View) : RecyclerView.ViewHolder(row)

        override fun onCreateViewHolder(parent: ViewGroup,
                                        viewType: Int): MyViewHolder {

            return VHItem(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.row_uploading_file, parent, false) as View
                )

        }



        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

            if (holder is VHItem) {
                val files = UploadingPost.INSTANCE.files
                val nameString = files[position]

                val name: TextView = holder.row.findViewById(R.id.label_uploading_file_name)
                val type: TextView = holder.row.findViewById(R.id.label_uploading_file_type)
                val size: TextView = holder.row.findViewById(R.id.label_uploading_file_size)



                name.text = nameString
                size.text = "$position.5 mb"



                val lastIndex = nameString.lastIndexOf(".")


                if (lastIndex > 0)
                    type.text = files[position].substring(lastIndex)
                else
                    type.text = "null"


                val cancel: Button = holder.row.findViewById(R.id.button_cancel_upload)

                cancel.setOnClickListener(object : View.OnClickListener {
                    override fun onClick(v: View?) {
                        Log.println(Log.WARN, "POSITION: ", position.toString())

                        files.removeAt(position)
//                        notifyItemRemoved(position)
                        notifyDataSetChanged()
                        holder.itemView.parent.requestLayout()
                    }
                })
            }
        }


        override fun getItemCount(): Int {
            return UploadingPost.INSTANCE.files.size
        }

//        override fun getItemViewType(position: Int): Int {
//            return if (isPositionHeader(position)) TYPE_HEADER else TYPE_BODY
//        }

//        private fun isPositionHeader(position: Int): Boolean {
//            return position == 0
//        }

//        private fun getItem(position: Int): String? {
//            return myDataset[position - 1]
//        }

        internal class VHItem(itemView: View) :
            MyViewHolder(itemView)

//        internal class VHHeader(itemView: View) :
//            MyViewHolder(itemView)
    }
}