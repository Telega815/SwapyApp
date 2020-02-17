package com.example.swapyapp.ui.fragments.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.swapyapp.MainActivity
import com.example.swapyapp.R

class SearchFragment : Fragment() {

    private lateinit var searchViewModel: SearchViewModel
    private lateinit var listView:ListView
    val names = arrayOf("gur019", "sei666" ,"telega815", "lees", "sos", "piska", "tanya")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        searchViewModel =
            ViewModelProviders.of(this).get(SearchViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_search, container, false)

        listView = root.findViewById(R.id.list_fav_feeds)

//        val textView: TextView = root.findViewById(R.id.text_home)
//        searchViewModel.text.observe(this, Observer {
//            textView.text = it
//        })

        val adapter = FavFeedsListAdapter(MainActivity.MAIN_ACTIVITY, names)

        listView.adapter = adapter

        listView.setOnItemClickListener { parent, view, position, id ->
            Toast.makeText(context, "Clicked item : $position",Toast.LENGTH_SHORT).show()
        }

        return root
    }

    class FavFeedsListAdapter(context: Context, private val names: Array<String>)
        : ArrayAdapter<String>(context, R.layout.row_fav_feed, R.id.label_fav_profile_name, names) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val layoutInflater:LayoutInflater = MainActivity.MAIN_ACTIVITY.applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            val row:View = layoutInflater.inflate(R.layout.row_fav_feed, parent, false)

            val name:TextView = row.findViewById(R.id.label_fav_profile_name)
            val charLabel:TextView = row.findViewById(R.id.label_profile_char)
            val uploadIcon:ImageView = row.findViewById(R.id.image_fav_feed_upload)


            name.text = names[position]
            charLabel.text = names[position][0].toString().toUpperCase()
            uploadIcon.setImageResource(R.drawable.ic_file_upload_white_50opacity_36dp)

            return row
        }
    }
}