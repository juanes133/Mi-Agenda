package com.example.miagenda.features.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.miagenda.ContactsActivity

open class DiaryFragment : Fragment(){
    lateinit var diaryActivity: ContactsActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        diaryActivity = (activity as ContactsActivity)
    }
}