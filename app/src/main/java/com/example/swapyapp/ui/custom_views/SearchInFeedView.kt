package com.example.swapyapp.ui.custom_views

import android.content.Context
import android.util.AttributeSet
import android.widget.SearchView
import android.widget.Toast

class SearchInFeedView : SearchView{



    constructor(context: Context) : super(context)
    constructor(context:Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context:Context, attrs: AttributeSet, defStyleAttr:Int) : super(context, attrs, defStyleAttr)
    constructor(context:Context, attrs: AttributeSet, defStyleAttr:Int, defStyleRes:Int) : super(context, attrs, defStyleAttr, defStyleRes)


    override fun onActionViewExpanded() {
        super.onActionViewExpanded()

        Toast.makeText(context, "SSSSSSSSSSSSSSSS", Toast.LENGTH_SHORT).show()
    }
}