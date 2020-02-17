package com.example.swapyapp.ui.fragments.addpost

class UploadingPost private constructor() {

    companion object {
        val INSTANCE:UploadingPost = UploadingPost()
    }

    var comment:String = ""
    var files:MutableList<String> = mutableListOf()

//    var fileMap:MutableMap<Int, String> = mutableMapOf()
}

