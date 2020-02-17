package com.example.swapyapp.ui.fragments.feed

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.swapyapp.MainActivity
import com.example.swapyapp.R
import java.text.SimpleDateFormat
import java.util.*

class FeedFragment : Fragment() {

    private lateinit var feedViewModel: FeedViewModel
    private lateinit var recyclerView: RecyclerView
//    val names = arrayOf("gur019", "sei666" ,"telega815", "lees", "sos", "piska", "tanya")
    val names = arrayOf("gur019", "sei666" ,"telega815", "lees", "sos", "piska", "tanya")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        feedViewModel =
            ViewModelProviders.of(this).get(FeedViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_my_feed, container, false)


        val searchView:SearchView = root.findViewById(R.id.feed_search)


        searchView.setOnSearchClickListener {object : View.OnClickListener {
            override fun onClick(v: View?) {
                Toast.makeText(context, "SSSSSSSSSSSSSSSS",Toast.LENGTH_SHORT).show()
                Log.println(Log.WARN,"SAS","AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
            }

        }
        }


        searchView.setOnQueryTextFocusChangeListener(object : View.OnFocusChangeListener{
            override fun onFocusChange(v: View?, hasFocus: Boolean) {
                if (hasFocus){
                    val layoutFavorite: ConstraintLayout = root.findViewById(R.id.layout_feed_only_favorite)
                    layoutFavorite.visibility = View.VISIBLE

//                    val layoutHeader: LinearLayout = root.findViewById(R.id.layout_feed_owner_info_header)
//                    layoutHeader.visibility = View.GONE
                }else{
                    val layoutFavorite: ConstraintLayout = root.findViewById(R.id.layout_feed_only_favorite)
                    layoutFavorite.visibility = View.GONE

//                    val layoutHeader: LinearLayout = root.findViewById(R.id.layout_feed_owner_info_header)
//                    layoutHeader.visibility = View.VISIBLE
                }
            }

        })

//        searchView.setOnQueryTextListener( object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                Toast.makeText(context, "SSSSSSSSSSSSSSSS",Toast.LENGTH_SHORT).show()
//                Log.println(Log.WARN,"SAS","AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
//                return false
//            }
//
//        }
//        )



        val viewManager = LinearLayoutManager(context)
        val viewAdapter = PostRecyclerAdapter(names)

        recyclerView = root.findViewById<RecyclerView>(R.id.recycler_feed_post).apply {
            setHasFixedSize(true)

            layoutManager = viewManager

            adapter = viewAdapter
        }

        return root
    }


    class PostRecyclerAdapter(private val myDataset: Array<String>) :
        RecyclerView.Adapter<PostRecyclerAdapter.MyViewHolder>() {
        private val TYPE_HEADER = 0
        private val TYPE_ITEM = 1

        open class MyViewHolder(val row: View) : RecyclerView.ViewHolder(row)

        val fileNames = arrayOf("MarcSQL 1.16 RUSMARC1.zip", "MarcSQL 1.16 RUSMARC1.zip" ,"MarcSQL 1.16 RUSMARC1.zip", "MarcSQL 1.16 RUSMARC1.zip")

        override fun onCreateViewHolder(parent: ViewGroup,
                                        viewType: Int): MyViewHolder {

            return if (viewType == TYPE_HEADER) {
                VHHeader(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.post_my_feed_list_header, parent, false) as View
                )
            }else {
                VHItem(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.row_feed_post, parent, false) as View
                )
            }
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

            if (holder is VHItem) {
                val itemPos = position-1
//                val dataItem = getItem(position)

                val comment: TextView = holder.row.findViewById(R.id.text_post_comment)
                val showAll: TextView = holder.row.findViewById(R.id.button_post_comment_show_all)

                showAll.setOnClickListener(object : View.OnClickListener {
                    override fun onClick(v: View?) {
                        if (comment.maxLines != 3){
                            comment.maxLines = 3
                            showAll.text = "Show all ..."
                        }else{
                            comment.maxLines = Int.MAX_VALUE
                            showAll.text = "Hide ..."
                        }

                    }

                })


                val name: TextView = holder.row.findViewById(R.id.label_post_owner_name)
                val charLabel: TextView = holder.row.findViewById(R.id.labal_post_owner_char)
                val date: TextView = holder.row.findViewById(R.id.label_post_owner_created_date)
                val lineIcon: ImageView = holder.row.findViewById(R.id.image_post_divide_line)
                val menuIcon: ImageButton = holder.row.findViewById(R.id.button_post_owner_menu)


                name.text = myDataset[itemPos]
                charLabel.text = myDataset[itemPos][0].toString().toUpperCase()

                val format = SimpleDateFormat("dd.MM.yyyy HH:mm")

                date.text = format.format(Date())
                lineIcon.setImageResource(R.drawable.bg_post_comment_line)
                menuIcon.setImageResource(R.drawable.ic_keyboard_arrow_down_white_36dp)


                //---------------------------------------set recycler file list---------------------------------------------------------------
                val viewManager = LinearLayoutManager(MainActivity.MAIN_ACTIVITY)
                val viewAdapter = FileRecyclerAdapter(fileNames)

                val recyclerView = holder.row.findViewById<RecyclerView>(R.id.recycler_post_file).apply {
                    setHasFixedSize(true)

                    layoutManager = viewManager


                    adapter = viewAdapter
                }
            }
//            else if (holder is VHHeader) {
//
//            }
        }


        override fun getItemCount(): Int {
            return myDataset.size + 1
        }

        override fun getItemViewType(position: Int): Int {
            return if (isPositionHeader(position)) TYPE_HEADER else TYPE_ITEM
        }

        private fun isPositionHeader(position: Int): Boolean {
            return position == 0
        }

        private fun getItem(position: Int): String? {
            return myDataset[position - 1]
        }

        internal class VHItem(itemView: View) :
            MyViewHolder(itemView)

        internal class VHHeader(itemView: View) :
            MyViewHolder(itemView)
    }

    class FileRecyclerAdapter(private val myDataset: Array<String>) :
        RecyclerView.Adapter<FileRecyclerAdapter.MyViewHolder>() {

        class MyViewHolder(val row: View) : RecyclerView.ViewHolder(row)


        override fun onCreateViewHolder(parent: ViewGroup,
                                        viewType: Int): MyViewHolder {
            val row = LayoutInflater.from(parent.context)
                .inflate(R.layout.row_file, parent, false) as View

            return MyViewHolder(row)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val name: TextView = holder.row.findViewById(R.id.label_file_name)
            val size: TextView = holder.row.findViewById(R.id.label_file_size)
            val type: TextView = holder.row.findViewById(R.id.label_file_type)
            val fileIcon: ImageView = holder.row.findViewById(R.id.image_file)
            val menuIcon: ImageButton = holder.row.findViewById(R.id.button_file_menu)


            name.text = myDataset[position]
            size.text = "$position.5 mb"


            val lastIndex = myDataset[position].lastIndexOf(".")
            if (lastIndex > 0)
                type.text = myDataset[position].substring(lastIndex)
            else
                type.text = "null"

            fileIcon.setImageResource(R.drawable.ic_insert_drive_file_white_50dp)
            menuIcon.setImageResource(R.drawable.ic_keyboard_arrow_down_white_36dp)
        }

        override fun getItemCount() = myDataset.size
    }
}