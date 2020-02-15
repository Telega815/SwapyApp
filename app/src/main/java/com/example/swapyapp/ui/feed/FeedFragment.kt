package com.example.swapyapp.ui.feed

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.marginBottom
import androidx.core.view.marginStart
import androidx.core.view.marginTop
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.swapyapp.MainActivity
import com.example.swapyapp.R
import java.text.SimpleDateFormat
import java.util.*

class FeedFragment : Fragment() {

    private lateinit var feedViewModel: FeedViewModel
    private lateinit var listView: ListView
    val names = arrayOf("gur019", "sei666" ,"telega815", "lees", "sos", "piska", "tanya")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        feedViewModel =
            ViewModelProviders.of(this).get(FeedViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_my_feed, container, false)
//        val textView: TextView = root.findViewById(R.id.text_notifications)
//        notificationsViewModel.text.observe(this, Observer {
//            textView.text = it
//        })

        listView = root.findViewById(R.id.list_feed_posts)

        listView.addHeaderView(createHeader("S"))

        val adapter = PostListAdapter(MainActivity.MAIN_ACTIVITY, names)

        listView.adapter = adapter

        listView.setOnItemClickListener { parent, view, position, id ->
            Toast.makeText(context, "Clicked item : $position",Toast.LENGTH_SHORT).show()
        }

        return root
    }

    fun createHeader(text: String?): View? {
        val v: View = layoutInflater.inflate(R.layout.post_list_header, null)
//        (v.findViewById<View>(R.id.tvText) as TextView).text = text
        return v
    }

    class PostListAdapter(context: Context, private val names: Array<String>)
        : ArrayAdapter<String>(context, R.layout.row_feed_post, R.id.label_post_owner_name, names) {

        val fileNames = arrayOf("MarcSQL 1.16 RUSMARC1.zip", "MarcSQL 1.16 RUSMARC1.zip" ,"MarcSQL 1.16 RUSMARC1.zip", "MarcSQL 1.16 RUSMARC1.zip")
//        val fileNames = arrayOf("MarcSQL 1.16 RUSMARC1.zip", "MarcSQL 1.16 RUSMARC1.zip")

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val layoutInflater:LayoutInflater = MainActivity.MAIN_ACTIVITY.applicationContext.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            val row:View = layoutInflater.inflate(R.layout.row_feed_post, parent, false)

            val name: TextView = row.findViewById(R.id.label_post_owner_name)
            val charLabel: TextView = row.findViewById(R.id.labal_post_owner_char)
            val date: TextView = row.findViewById(R.id.label_post_owner_created_date)
            val lineIcon: ImageView = row.findViewById(R.id.image_post_divide_line)
            val menuIcon: ImageButton = row.findViewById(R.id.button_post_owner_menu)


            name.text = names[position]
            charLabel.text = names[position][0].toString().toUpperCase()

            val format = SimpleDateFormat("dd.mm.yyyy hh:mm")

            date.text = format.format(Date())
            lineIcon.setImageResource(R.drawable.bg_post_comment_line)
            menuIcon.setImageResource(R.drawable.ic_keyboard_arrow_down_white_36dp)



            //-------------------------------------------------------------------------

            val viewManager = LinearLayoutManager(context)
            val viewAdapter = MyAdapter(fileNames)

            val divider =DividerItemDecoration(MainActivity.MAIN_ACTIVITY, DividerItemDecoration.VERTICAL)

            context.getDrawable(R.drawable.divider_file)?.let { divider.setDrawable(it) }

            val recyclerView = row.findViewById<RecyclerView>(R.id.recycler_post_file).apply {
                // use this setting to improve performance if you know that changes
                // in content do not change the layout size of the RecyclerView
                setHasFixedSize(true)

                // use a linear layout manager
                layoutManager = viewManager

                addItemDecoration(divider)

                // specify an viewAdapter (see also next example)
                adapter = viewAdapter
            }



//            val listView: RecyclerView = row.findViewById(R.id.recycler_post_file)
//
//            val listadp: ListAdapter = FileListAdapter(context, fileNames)
//
//            listView.adapter = listadp
//
//
//
//            val max = listadp.count-1
//
//            var totalHeight = 0
////            var totalHeight = 24*listadp.count
//
//            for (i in 0..max) {
//                val listItem:View = listadp.getView(i, null, listView)
//                listItem.measure(0, 0)
//                totalHeight += listItem.measuredHeight
//            }
//
//
//            val params = listView.layoutParams
//            params.height = totalHeight + (listView.dividerHeight * (listadp.count - 1))
//            listView.layoutParams = params
//            listView.requestLayout()

            return row
        }
    }

    class MyAdapter(private val myDataset: Array<String>) :
        RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder.
        // Each data item is just a string in this case that is shown in a TextView.
        class MyViewHolder(val row: View) : RecyclerView.ViewHolder(row)


        // Create new views (invoked by the layout manager)
        override fun onCreateViewHolder(parent: ViewGroup,
                                        viewType: Int): MyViewHolder {
            // create a new view
            val row = LayoutInflater.from(parent.context)
                .inflate(R.layout.row_file, parent, false) as View
            // set the view's size, margins, paddings and layout parameters


            return MyViewHolder(row)
        }

        // Replace the contents of a view (invoked by the layout manager)
        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element

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

                    fileIcon.setImageResource(R.drawable.ic_insert_drive_file_white_72dp)
            menuIcon.setImageResource(R.drawable.ic_keyboard_arrow_down_white_36dp)

//            holder.textView.text = myDataset[position]
        }

        // Return the size of your dataset (invoked by the layout manager)
        override fun getItemCount() = myDataset.size
    }

//    class FileListAdapter(context: Context, private val names: Array<String>)
//        : ArrayAdapter<String>(context, R.layout.row_file, R.id.label_file_name, names) {
//
//        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
//            //-----------------------------------------------------------------------
//            val layoutInflater:LayoutInflater = MainActivity.MAIN_ACTIVITY.applicationContext.getSystemService(
//                Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//
//            val row:View = layoutInflater.inflate(R.layout.row_file, parent, false)
//
//            val name: TextView = row.findViewById(R.id.label_file_name)
//            val size: TextView = row.findViewById(R.id.label_file_size)
//            val type: TextView = row.findViewById(R.id.label_file_type)
//            val fileIcon: ImageView = row.findViewById(R.id.image_file)
//            val menuIcon: ImageButton = row.findViewById(R.id.button_file_menu)
//
//
//            name.text = names[position]
//            size.text = position.toString() + ".5 mb"
//            type.text = names[position].substring(names[position].lastIndexOf("."))
//
//            fileIcon.setImageResource(R.drawable.ic_insert_drive_file_white_72dp)
//            menuIcon.setImageResource(R.drawable.ic_keyboard_arrow_down_white_36dp)
//
//            return row
//        }
//    }
}